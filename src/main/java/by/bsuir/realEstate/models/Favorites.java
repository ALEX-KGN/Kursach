package by.bsuir.realEstate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="favorites")
public class Favorites {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "accountid", referencedColumnName = "id")
    private Account accountFavorites;

    @OneToMany(mappedBy = "favorites")
    List<FavoriteApartment> favoriteApartments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccountFavorites() {
        return accountFavorites;
    }

    public void setAccountFavorites(Account accountFavorites) {
        this.accountFavorites = accountFavorites;
    }

    public List<FavoriteApartment> getFavoriteApartments() {
        return favoriteApartments;
    }

    public void setFavoriteApartments(List<FavoriteApartment> favoriteApartments) {
        this.favoriteApartments = favoriteApartments;
    }

    public Favorites(Account accountFavorites) {
        this.accountFavorites = accountFavorites;
    }

    public Favorites() {
    }

    public Favorites(int id, Account accountFavorites, List<FavoriteApartment> favoriteApartments) {
        this.id = id;
        this.accountFavorites = accountFavorites;
        this.favoriteApartments = favoriteApartments;
    }
}
