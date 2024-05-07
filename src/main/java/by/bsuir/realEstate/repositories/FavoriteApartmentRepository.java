package by.bsuir.realEstate.repositories;

import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.FavoriteApartment;
import by.bsuir.realEstate.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteApartmentRepository extends JpaRepository<FavoriteApartment, Integer> {
    Optional<FavoriteApartment> findByFavoritesAndApartment(Favorites favorites, Apartment apartment);
    List<FavoriteApartment> findAllByFavorites(Favorites favorites);
}
