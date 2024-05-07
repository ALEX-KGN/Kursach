package by.bsuir.realEstate.services;

import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.repositories.AccountRepository;
import by.bsuir.realEstate.security.AccountDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(s);

        if (account.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new AccountDetails(account.get());
    }

    public Account loadAccountByUsername(String username){
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return account.get();
    }

    public List<Account> loadAllAccounts(){
        return accountRepository.findAll();
    }

}
