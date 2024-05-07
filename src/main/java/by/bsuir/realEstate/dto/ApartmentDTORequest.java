package by.bsuir.realEstate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ApartmentDTORequest {
    @NotNull(message = "Цена не может быть пустой")
    private int price;

    @NotNull(message = "Площадь не может быть пустой")
    private int square;

    @NotNull(message = "Количество комнат не может быть пустым")
    private int numberOfRooms;

    @NotNull(message = "Тип не может быть пустым")
    private int typeId;

    @NotEmpty(message = "Страна не может быть пустым")
    private String country;

    @NotEmpty(message = "Улица не может быть пустой")
    private String street;

    @NotEmpty(message = "Город не может быть пустым")
    private String city;

    @NotEmpty(message = "Номер дома не может быть пустым")
    private String numberHouse;

    @NotNull(message = "Картинки не могут быть пустыми")
    private List<MultipartFile> images;

    public ApartmentDTORequest() {
    }

    public int getPrice() {
        return price;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(String numberHouse) {
        this.numberHouse = numberHouse;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public ApartmentDTORequest(int price, int square, int numberOfRooms, int typeId, String country, String street, String city, String numberHouse, List<MultipartFile> images) {
        this.price = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.typeId = typeId;
        this.country = country;
        this.street = street;
        this.city = city;
        this.numberHouse = numberHouse;
        this.images = images;
    }
}
