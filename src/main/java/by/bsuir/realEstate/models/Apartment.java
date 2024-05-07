package by.bsuir.realEstate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price")
    @NotNull(message = "Цена не может быть пустой")
    private int price;

    @Column(name = "square")
    @NotNull(message = "Площадь не может быть пустой")
    private int square;

    @Column(name = "numberofrooms")
    @NotNull(message = "Количество комнат не может быть пустым")
    private int numberOfRooms;

    @ManyToOne()
    @JoinColumn(name="typeid", referencedColumnName = "id")
    @JsonIgnore
    private Type typeApartment;

    @ManyToOne()
    @JoinColumn(name="addressid", referencedColumnName = "id")
    @JsonIgnore
    private Address addressApartment;

    @ManyToOne()
    @JoinColumn(name="accountid", referencedColumnName = "id")
    @JsonIgnore
    private Account accountApartment;

    @OneToMany(mappedBy = "imageApartment")
    List<Image> images;

    @OneToMany(mappedBy = "apartment")
    List<FavoriteApartment> favoriteApartments;

    public Apartment(int price, int square, int numberOfRooms, Type typeApartment, Address addressApartment, List<Image> images, List<FavoriteApartment> favoriteApartments) {
        this.price = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.typeApartment = typeApartment;
        this.addressApartment = addressApartment;
        this.images = images;
        this.favoriteApartments = favoriteApartments;
    }

    public Apartment(int price, int square, int numberOfRooms, Type typeApartment, Address addressApartment, Account accountApartment) {
        this.price = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.typeApartment = typeApartment;
        this.addressApartment = addressApartment;
        this.accountApartment = accountApartment;
    }

    public Apartment(int id, int price, int square, int numberOfRooms, Type typeApartment, Address addressApartment, List<Image> images, List<FavoriteApartment> favoriteApartments) {
        this.id = id;
        this.price = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.typeApartment = typeApartment;
        this.addressApartment = addressApartment;
        this.images = images;
        this.favoriteApartments = favoriteApartments;
    }

    public Apartment(int price, int square, int numberOfRooms, Type typeApartment, Address addressApartment) {
        this.price = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.typeApartment = typeApartment;
        this.addressApartment = addressApartment;
    }

    public Apartment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public Account getAccountApartment() {
        return accountApartment;
    }

    public void setAccountApartment(Account accountApartment) {
        this.accountApartment = accountApartment;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Type getTypeApartment() {
        return typeApartment;
    }

    public void setTypeApartment(Type typeApartment) {
        this.typeApartment = typeApartment;
    }

    public Address getAddressApartment() {
        return addressApartment;
    }

    public void setAddressApartment(Address addressApartment) {
        this.addressApartment = addressApartment;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<FavoriteApartment> getFavoriteApartments() {
        return favoriteApartments;
    }

    public void setFavoriteApartments(List<FavoriteApartment> favoriteApartments) {
        this.favoriteApartments = favoriteApartments;
    }
}
