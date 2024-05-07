package by.bsuir.realEstate.controllers; // запросы к среверу

import by.bsuir.realEstate.dto.ApartmentDTORequest;
import by.bsuir.realEstate.dto.ApartmentDTOResponse;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.security.JWTUtil;
import by.bsuir.realEstate.services.AccountDetailsService;
import by.bsuir.realEstate.services.ApartmentService;
import by.bsuir.realEstate.utils.AccessException;
import by.bsuir.realEstate.utils.ApartmentDTORequestValidator;
import by.bsuir.realEstate.utils.ApartmentFilter;
import by.bsuir.realEstate.utils.Converter;
import by.bsuir.realEstate.utils.ErrorResponse;
import by.bsuir.realEstate.utils.ObjectNotCreatedException;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/apartments")
@CrossOrigin
@RestController
public class ApartmentController {
    private final JWTUtil jwtUtil;
    private final AccountDetailsService accountDetailsService;
    private final ApartmentService apartmentService;
    private final ApartmentDTORequestValidator apartmentDTORequestValidator;

    public ApartmentController(JWTUtil jwtUtil, AccountDetailsService accountDetailsService, ApartmentService apartmentService, ApartmentDTORequestValidator apartmentDTORequestValidator) {
        this.jwtUtil = jwtUtil;
        this.accountDetailsService = accountDetailsService;
        this.apartmentService = apartmentService;
        this.apartmentDTORequestValidator = apartmentDTORequestValidator;
    }

    @GetMapping
    public List<ApartmentDTOResponse> getAll(){
        return apartmentService.getAllApartments();
    }

    @GetMapping("/foraccount")
    public List<ApartmentDTOResponse> getAllForAccount(@RequestHeader("Authorization") String token){
        Account account = parseUsername(token);
        return apartmentService.getAllApartmentsForAccount(account);
    }

    @GetMapping("/filter")
    public List<ApartmentDTOResponse> getFilter(@ModelAttribute ApartmentFilter filter){
        return apartmentService.getApartmentsByFilter(filter);
    }

    @GetMapping("/{id}")
    public ApartmentDTOResponse getById(@PathVariable("id") int id) throws ObjectNotFoundException {
        return apartmentService.getApartment(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@Valid @ModelAttribute ApartmentDTORequest apartmentDTORequest,
                                             @RequestHeader("Authorization") String token,
                                             BindingResult bindingResult) throws ObjectNotCreatedException {
        Account account = parseUsername(token);
        apartmentDTORequestValidator.validate(apartmentDTORequest,bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        apartmentService.createApartment(apartmentDTORequest, account);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestHeader("Authorization") String token,
                                             @PathVariable("id") int id) throws ObjectNotCreatedException, AccessException, ObjectNotFoundException {
        Account account = parseUsername(token);
        apartmentService.deleteApartment(id, account);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> put(@RequestHeader("Authorization") String token,
                                          @PathVariable("id") int id,
                                          @RequestBody Map<String, Integer> price) throws ObjectNotCreatedException, AccessException, ObjectNotFoundException {
        Account account = parseUsername(token);
        apartmentService.updateApartment(id, account, price.get("price").intValue());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Account parseUsername(String token){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7));
        return accountDetailsService.loadAccountByUsername(username);
    }

    @ExceptionHandler //обработчик ошибок
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AccessException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
