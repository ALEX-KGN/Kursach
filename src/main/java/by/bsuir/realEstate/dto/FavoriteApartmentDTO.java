package by.bsuir.realEstate.dto;

public class FavoriteApartmentDTO {
    private ApartmentDTOResponse apartmentDTOResponse;
    private int id;

    public FavoriteApartmentDTO() {
    }

    public FavoriteApartmentDTO(ApartmentDTOResponse apartmentDTOResponse, int id) {
        this.apartmentDTOResponse = apartmentDTOResponse;
        this.id = id;
    }

    public ApartmentDTOResponse getApartmentDTOResponse() {
        return apartmentDTOResponse;
    }

    public void setApartmentDTOResponse(ApartmentDTOResponse apartmentDTOResponse) {
        this.apartmentDTOResponse = apartmentDTOResponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
