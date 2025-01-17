package by.bsuir.realEstate.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name="address")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country")
    @NotEmpty(message = "Страна не может быть пустой")
    private String country;

    @Column(name = "city")
    @NotEmpty(message = "Город не может быть пустым")
    private String city;

    @Column(name = "street")
    @NotEmpty(message = "Улица не может быть пустой")
    private String street;

    @Column(name = "numberhouse")
    @NotEmpty(message = "Номер дома не может быть пустым")
    private String number;

    @OneToMany(mappedBy = "addressApartment")
    @JsonIgnore
    List<Apartment> apartments;

    public Address(int id, String country, String city, String street, String number, List<Apartment> apartments) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.apartments = apartments;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public Address(String country, String city, String street, String number, List<Apartment> apartments) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.apartments = apartments;
    }

    public Address(String country, String city, String street, String number_house) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number_house;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number_house) {
        this.number = number_house;
    }

    public Address() {
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

}
