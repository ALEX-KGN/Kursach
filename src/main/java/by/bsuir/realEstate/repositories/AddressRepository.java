package by.bsuir.realEstate.repositories;

import by.bsuir.realEstate.models.Address;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
