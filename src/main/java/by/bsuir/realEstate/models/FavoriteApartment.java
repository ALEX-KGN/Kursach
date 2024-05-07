package by.bsuir.realEstate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favoriteapartment")
public class FavoriteApartment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "favoriteid", referencedColumnName = "id")
    private Favorites favorites;

    @ManyToOne
    @JoinColumn(name = "apartmentid", referencedColumnName = "id")
    private Apartment apartment;

    public FavoriteApartment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public FavoriteApartment(Favorites favorites, Apartment apartment) {
        this.favorites = favorites;
        this.apartment = apartment;
    }

    public FavoriteApartment(int id, Favorites favorites, Apartment apartment) {
        this.id = id;
        this.favorites = favorites;
        this.apartment = apartment;
    }
}
