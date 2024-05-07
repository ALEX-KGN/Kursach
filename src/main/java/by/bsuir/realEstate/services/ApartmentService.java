package by.bsuir.realEstate.services;

import by.bsuir.realEstate.dto.ApartmentDTORequest;
import by.bsuir.realEstate.dto.ApartmentDTOResponse;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.models.Address;
import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.Image;
import by.bsuir.realEstate.models.Type;
import by.bsuir.realEstate.repositories.AddressRepository;
import by.bsuir.realEstate.repositories.ApartmentRepository;
import by.bsuir.realEstate.repositories.ImageRepository;
import by.bsuir.realEstate.repositories.TypeRepository;
import by.bsuir.realEstate.utils.AccessException;
import by.bsuir.realEstate.utils.ApartmentFilter;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final AddressRepository addressRepository;
    private final TypeRepository typeRepository;
    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;
    private final CurrencyService currencyService;

    public ApartmentService(ApartmentRepository apartmentRepository, AddressRepository addressRepository, TypeRepository typeRepository, FileStorageService fileStorageService, ImageRepository imageRepository, CurrencyService currencyService) {
        this.apartmentRepository = apartmentRepository;
        this.addressRepository = addressRepository;
        this.typeRepository = typeRepository;
        this.fileStorageService = fileStorageService;
        this.imageRepository = imageRepository;
        this.currencyService = currencyService;
    }

    public List<ApartmentDTOResponse> getAllApartments(){
        List<Apartment> apartmentList = apartmentRepository.findAll();
        return convertToApartmentDTOResponseList(apartmentList);
    }

    public List<ApartmentDTOResponse> getAllApartmentsForAccount(Account account){
        List<Apartment> apartmentList = apartmentRepository.findAllByAccountApartment(account);
        return convertToApartmentDTOResponseList(apartmentList);
    }


    public ApartmentDTOResponse getApartment(int id) throws ObjectNotFoundException {
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        if(apartmentOptional.isEmpty()){
            throw new ObjectNotFoundException("Недвижимость не найдена");
        }
        double usd = currencyService.getCurrencies();
        return convertToApartmentDTOResponse(apartmentOptional.get(), usd);
    }

    public List<ApartmentDTOResponse> getApartmentsByFilter(ApartmentFilter apartmentFilter){
       ApartmentFilter apartmentFilter1 = new ApartmentFilter(typeRepository);
       apartmentFilter1.setTypeid(apartmentFilter.getTypeid());
       apartmentFilter1.setSquare(apartmentFilter.getSquare());
       apartmentFilter1.setMaxPrice(apartmentFilter.getMaxPrice());
       apartmentFilter1.setMinPrice(apartmentFilter.getMinPrice());
       apartmentFilter1.setNumberofrooms(apartmentFilter.getNumberofrooms());
        List<Apartment> apartments = apartmentRepository.findAll(apartmentFilter1);
       return convertToApartmentDTOResponseList(apartments);
    }

    @Transactional //транзакция в бд
    public void createApartment(ApartmentDTORequest apartmentDTORequest, Account account){
        Address address = new Address(apartmentDTORequest.getCountry(),
                apartmentDTORequest.getCity(),
                apartmentDTORequest.getStreet(),
                apartmentDTORequest.getNumberHouse());
        addressRepository.save(address);
        Type type = typeRepository.findById(apartmentDTORequest.getTypeId()).get();
        Apartment apartment = new Apartment(apartmentDTORequest.getPrice(),
                apartmentDTORequest.getSquare(),
                apartmentDTORequest.getNumberOfRooms(),
                type,
                address,
                account);
        apartmentRepository.save(apartment);
        for(MultipartFile multipartFile: apartmentDTORequest.getImages()){
            String name = fileStorageService.storeFile(multipartFile);
            Image image = new Image(name, apartment);
            imageRepository.save(image);
        }
    }

    @Transactional
    public void deleteApartment(int id, Account account) throws ObjectNotFoundException, AccessException {
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        if(apartmentOptional.isEmpty()){
            throw new ObjectNotFoundException("Недвижимость не найдена");
        }
        Apartment apartment = apartmentOptional.get();
        if(apartment.getAccountApartment().equals(account)||account.getRole().equals("ROLE_ADMIN")){
            List<Image> images = apartment.getImages();
            for(Image image: images){
                fileStorageService.deleteFile(image.getName());
            }
            apartmentRepository.delete(apartment);
        }
        else{
            throw new AccessException("Доступ запрещен");
        }
    }

    @Transactional
    public void updateApartment(int id, Account account, int price) throws ObjectNotFoundException, AccessException {
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        if(apartmentOptional.isEmpty()){
            throw new ObjectNotFoundException("Недвижимость не найдена");
        }
        Apartment apartment = apartmentOptional.get();
        if(apartment.getAccountApartment().equals(account)){
            apartment.setPrice(price);
            apartmentRepository.save(apartment);
        }
        else{
            throw new AccessException("Доступ запрещен");
        }
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

    private List<ApartmentDTOResponse> convertToApartmentDTOResponseList(List<Apartment> apartmentList){
        List<ApartmentDTOResponse> apartmentDTOResponses = new ArrayList<ApartmentDTOResponse>();
       double usd = currencyService.getCurrencies();
        for(Apartment apartment: apartmentList){
            apartmentDTOResponses.add(convertToApartmentDTOResponse(apartment, usd));
        }
        return apartmentDTOResponses;
    }

}
