package by.bsuir.realEstate.services;
import by.bsuir.realEstate.models.Apartment;
import by.bsuir.realEstate.models.Type;
import by.bsuir.realEstate.repositories.ApartmentRepository;
import by.bsuir.realEstate.repositories.TypeRepository;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private TypeRepository typeRepository;
    private ApartmentRepository apartmentRepository;

    public TypeService(TypeRepository typeRepository, ApartmentRepository apartmentRepository) {
        this.typeRepository = typeRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional
    public void createType(Type type){
        typeRepository.save(type);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type loadTypeByName(String s) throws ObjectNotFoundException {
        Optional<Type> type  = typeRepository.findByName(s);
        return type.orElseThrow(ObjectNotFoundException::new);
    }

    public Type loadTypeById(int id) throws ObjectNotFoundException {
        Optional<Type> type  = typeRepository.findById(id);
        return type.orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional
    public void deleteType(int id) throws ObjectNotFoundException{
        Optional<Type> type = typeRepository.findById(id);
        if(type.isEmpty()){
            throw new ObjectNotFoundException("Товар не найден");
        }
        List<Apartment> applianceList = apartmentRepository.findAllByTypeApartment(type.get());
        if(!applianceList.isEmpty()){
            throw new ObjectNotFoundException("Невозможно удалить тип. Удалите соответствующую недвижимость");
        }
        typeRepository.delete(type.get());
    }

    @Transactional
    public void updateType(int id, Type type) throws ObjectNotFoundException {
        Optional<Type> type_old_optional = typeRepository.findById(id);
        if(type_old_optional.isEmpty()){
            throw new ObjectNotFoundException();
        }
        Type type_old = type_old_optional.get();
        type_old.setName(type.getName());
        typeRepository.save(type_old);
    }
}

