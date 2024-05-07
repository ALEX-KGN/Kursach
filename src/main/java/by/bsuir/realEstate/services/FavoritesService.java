package by.bsuir.realEstate.services;

import by.bsuir.realEstate.dto.ApartmentDTOResponse;
import by.bsuir.realEstate.dto.FavoriteApartmentDTO;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.FavoriteApartment;
import by.bsuir.realEstate.models.Image;
import by.bsuir.realEstate.repositories.ApartmentRepository;
import by.bsuir.realEstate.repositories.FavoriteApartmentRepository;
import by.bsuir.realEstate.repositories.FavoritesRepository;
import by.bsuir.realEstate.utils.AccessException;
import by.bsuir.realEstate.utils.ObjectNotCreatedException;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService {
    private final ApartmentRepository apartmentRepository;
    private final FavoritesRepository favoritesRepository;
    private final FavoriteApartmentRepository favoriteApartmentRepository;
    private final CurrencyService currencyService;

    public FavoritesService(ApartmentRepository apartmentRepository, FavoritesRepository favoritesRepository, FavoriteApartmentRepository favoriteApartmentRepository, CurrencyService currencyService) {
        this.apartmentRepository = apartmentRepository;
        this.favoritesRepository = favoritesRepository;
        this.favoriteApartmentRepository = favoriteApartmentRepository;
        this.currencyService = currencyService;
    }

    @Transactional
    public void add(int id, Account account) throws ObjectNotFoundException, ObjectNotCreatedException {
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        if(apartmentOptional.isEmpty()){
            throw new ObjectNotFoundException("Недвижимость не найдена");
        }
        Optional<FavoriteApartment> favoriteApartmentOptional = favoriteApartmentRepository.findByFavoritesAndApartment(account.getFavorites(), apartmentOptional.get());
        if(favoriteApartmentOptional.isPresent()){
            throw new ObjectNotCreatedException("Недвижимость уже добавлена в избранное");
        }
        FavoriteApartment favoriteApartment = new FavoriteApartment(account.getFavorites(), apartmentOptional.get());
        favoriteApartmentRepository.save(favoriteApartment);
    }

    public List<FavoriteApartmentDTO>  getAllFavorites(Account account){
        List<FavoriteApartment> favoriteApartmentList = favoriteApartmentRepository.findAllByFavorites(account.getFavorites());
        return convertToApartmentDTOResponseList(favoriteApartmentList);
    }

    @Transactional
    public void deleteApartment(int id, Account account) throws ObjectNotFoundException, AccessException {

        Optional<FavoriteApartment> favoriteApartmentOptional = favoriteApartmentRepository.findById(id);
        if(favoriteApartmentOptional.isEmpty()){
            throw new ObjectNotFoundException("Избранная недвижимость не найдена");
        }
        if (!favoriteApartmentOptional.get().getFavorites().equals(account.getFavorites())){
            throw new AccessException("Доступ запрещен");
        }
        favoriteApartmentRepository.delete(favoriteApartmentOptional.get());
    }

    private ApartmentDTOResponse convertToApartmentDTOResponse(Apartment apartment, double usd){
        ApartmentDTOResponse apartmentDTOResponse;
        List<String> images = new ArrayList<String>();
        for(Image image: apartment.getImages()){
            images.add(image.getName());
        }
        apartmentDTOResponse = new ApartmentDTOResponse(apartment.getId(),
                apartment.getPrice(),
                (int) (apartment.getPrice()/usd),
                apartment.getSquare(),
                apartment.getNumberOfRooms(),
                apartment.getTypeApartment(),
                apartment.getAddressApartment(),
                apartment.getAccountApartment().getPhoneNumber(),
                images);
        return apartmentDTOResponse;
    }

    private List<FavoriteApartmentDTO> convertToApartmentDTOResponseList(List<FavoriteApartment> favoriteApartmentList){
        List<FavoriteApartmentDTO> favoriteApartmentDTOList = new ArrayList<FavoriteApartmentDTO>();
        double usd = currencyService.getCurrencies();
        for(FavoriteApartment favoriteApartment: favoriteApartmentList){
            favoriteApartmentDTOList.add(new FavoriteApartmentDTO(convertToApartmentDTOResponse(favoriteApartment.getApartment(), usd),favoriteApartment.getId()));
        }
        return favoriteApartmentDTOList;
    }


}
