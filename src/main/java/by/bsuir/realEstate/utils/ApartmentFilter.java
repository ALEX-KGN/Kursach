package by.bsuir.realEstate.utils;

import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.Type;
import by.bsuir.realEstate.repositories.TypeRepository;
import by.bsuir.realEstate.services.TypeService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class ApartmentFilter implements Specification<Apartment> {
    private String minPrice;
    private String maxPrice;
    private String numberofrooms;
    private String square;
    private String typeid;

    private final TypeRepository typeRepository;

    @Autowired
    public ApartmentFilter(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Specification<Apartment> and(Specification<Apartment> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Apartment> or(Specification<Apartment> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(minPrice))
        {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (StringUtils.isNotBlank(maxPrice))
        {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (StringUtils.isNotBlank(square))
        {
            predicates.add(criteriaBuilder.equal(root.get("square"), square));
        }
        if (StringUtils.isNotBlank(numberofrooms))
        {
            predicates.add(criteriaBuilder.equal(root.get("numberOfRooms"), numberofrooms));
        }
        if (StringUtils.isNotBlank(typeid))
        {
            Type type = typeRepository.findById(Integer.valueOf(typeid)).get();
            predicates.add(criteriaBuilder.equal(root.get("typeApartment"), type));
        }
        return predicates.size() <= 0 ? null : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getNumberofrooms() {
        return numberofrooms;
    }

    public void setNumberofrooms(String numberofrooms) {
        this.numberofrooms = numberofrooms;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }


}
