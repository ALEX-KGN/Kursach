package by.bsuir.realEstate.repositories;

import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.models.Address;
import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    List<Apartment> findAllByTypeApartment(Type type);
    List<Apartment> findAllByAccountApartment(Account account);
}
