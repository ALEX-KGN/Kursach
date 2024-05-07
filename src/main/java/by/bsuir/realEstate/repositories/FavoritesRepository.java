package by.bsuir.realEstate.repositories;

import by.bsuir.realEstate.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
}
