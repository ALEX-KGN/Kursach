package by.bsuir.realEstate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название не может быть пустым")
    private String name;

    @ManyToOne()
    @JoinColumn(name="apartmentid", referencedColumnName = "id")
    @JsonIgnore
    private Apartment imageApartment;

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apartment getImageApartment() {
        return imageApartment;
    }

    public void setImageApartment(Apartment imageApartment) {
        this.imageApartment = imageApartment;
    }

    public Image(String name, Apartment imageApartment) {
        this.name = name;
        this.imageApartment = imageApartment;
    }

    public Image(int id, String name, Apartment imageApartment) {
        this.id = id;
        this.name = name;
        this.imageApartment = imageApartment;
    }
}
