package by.bsuir.realEstate.utils;

import by.bsuir.realEstate.dto.ApartmentDTORequest;
import by.bsuir.realEstate.models.Address;
import by.bsuir.realEstate.models.Type;
import by.bsuir.realEstate.repositories.AddressRepository;
import by.bsuir.realEstate.repositories.TypeRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ApartmentDTORequestValidator implements Validator {
    private final TypeRepository typeRepository;

    public ApartmentDTORequestValidator(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return ApartmentDTORequestValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApartmentDTORequest apartmentDTORequest = (ApartmentDTORequest) target;
        Optional<Type> type = typeRepository.findById(apartmentDTORequest.getTypeId());
        if(type.isEmpty()){
            errors.rejectValue("type", "", "Данный тип отсутствует");
        }
    }
}