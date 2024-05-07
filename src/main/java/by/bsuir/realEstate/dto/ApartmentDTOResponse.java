package by.bsuir.realEstate.dto;

import by.bsuir.realEstate.models.Address;
import by.bsuir.realEstate.models.Type;

import java.util.List;


public class ApartmentDTOResponse {
    private int id;
    private int price_byn;
    private int price_usd;
    private int square;
    private int numberOfRooms;
    private Type type;
    private Address address;
    private String phoneNumber;
    private List<String> images;

    public ApartmentDTOResponse() {
    }

    public int getPrice_byn() {
        return price_byn;
    }

    public void setPrice_byn(int price_byn) {
        this.price_byn = price_byn;
    }

    public int getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(int price_usd) {
        this.price_usd = price_usd;
    }

    public ApartmentDTOResponse(int id, int price_byn, int price_usd, int square, int numberOfRooms, Type type, Address address, String phoneNumber, List<String> images) {
        this.id = id;
        this.price_byn = price_byn;
        this.price_usd = price_usd;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ApartmentDTOResponse(int id, int price, int square, int numberOfRooms, Type type, Address address, String phoneNumber, List<String> images) {
        this.id = id;
        this.price_byn = price;
        this.square = square;
        this.numberOfRooms = numberOfRooms;
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.images = images;
    }
}
