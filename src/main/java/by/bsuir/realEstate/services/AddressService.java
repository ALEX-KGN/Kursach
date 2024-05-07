package by.bsuir.realEstate.services;

import by.bsuir.realEstate.models.Address;
import by.bsuir.realEstate.repositories.AddressRepository;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Address loadAddressById(int id) throws ObjectNotFoundException {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional
    public int createAddress(Address address){
        addressRepository.save(address);
        return address.getId();
    }

}