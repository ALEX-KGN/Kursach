package by.bsuir.realEstate.services;
import by.bsuir.realEstate.dto.AccountPutDTO;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.models.Favorites;
import by.bsuir.realEstate.repositories.AccountRepository;
import by.bsuir.realEstate.repositories.FavoritesRepository;
import by.bsuir.realEstate.utils.ObjectIsPresentException;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AccessException;
import java.util.Optional;


@Service
public class RegistrationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final FavoritesRepository favoritesRepository;

    public RegistrationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, FavoritesRepository favoritesRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.favoritesRepository = favoritesRepository;
    }

    @Transactional
    public void register(Account account, String role){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(role);
        accountRepository.save(account);
        Favorites favorites = new Favorites();
        favorites.setAccountFavorites(account);
        favoritesRepository.save(favorites);
    }

    @Transactional
    public void deleteAccount(int id, Account account) throws ObjectNotFoundException, AccessException {
        Optional<Account> account_opt = accountRepository.findById(id);
        if(account_opt.isEmpty()){
            throw new ObjectNotFoundException("Аккаунт не найден");
        }
        if(account.getId()==id){
            throw new AccessException("Невозможно удалить собственный аккаунт");
        }
        accountRepository.delete(account_opt.get());
    }

    @Transactional
    public Account update(AccountPutDTO accountPutDTO, int id, Account account) throws ObjectNotFoundException, AccessException, ObjectIsPresentException {
        Optional<Account> account_opt = accountRepository.findByUsername(account.getUsername());
        if(account_opt.isEmpty()){
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if(account_opt.get().getId()!=id){
            throw new AccessException("Доступ запрещен");
        }
        if(!passwordEncoder.matches(accountPutDTO.getOldPassword(),account_opt.get().getPassword())){
            throw new AccessException("Неверный пароль");
        }
        Account account_rep = account_opt.get();
        if(!accountPutDTO.getUsername().equals(account_rep.getUsername())){
            Optional<Account> account_repeat = accountRepository.findByUsername(accountPutDTO.getUsername());
            if(account_repeat.isPresent()){
                throw new ObjectIsPresentException("Данный email уже используется");
            }
            account_rep.setUsername(accountPutDTO.getUsername());
        }
        if(!accountPutDTO.getNewPassword().isEmpty()){
            account_rep.setPassword(passwordEncoder.encode(accountPutDTO.getNewPassword()));
        }
        if(accountPutDTO.getPhoneNumber()!=null){
            account_rep.setPhoneNumber(accountPutDTO.getPhoneNumber());
        }
        accountRepository.save(account_rep);
        return account_rep;
    }
}
