package by.bsuir.realEstate.controllers;

import by.bsuir.realEstate.dto.ApartmentDTOResponse;
import by.bsuir.realEstate.dto.FavoriteApartmentDTO;
import by.bsuir.realEstate.models.Account;
import by.bsuir.realEstate.security.JWTUtil;
import by.bsuir.realEstate.services.AccountDetailsService;
import by.bsuir.realEstate.services.FavoritesService;
import by.bsuir.realEstate.utils.AccessException;
import by.bsuir.realEstate.utils.ErrorResponse;
import by.bsuir.realEstate.utils.ObjectNotCreatedException;
import by.bsuir.realEstate.utils.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/favorites")
@CrossOrigin
@RestController
public class FavoritesController {
    private final FavoritesService favoritesService;
    private final JWTUtil jwtUtil;
    private final AccountDetailsService accountDetailsService;

    public FavoritesController(FavoritesService favoritesService, JWTUtil jwtUtil, AccountDetailsService accountDetailsService) {
        this.favoritesService = favoritesService;
        this.jwtUtil = jwtUtil;
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping
    public List<FavoriteApartmentDTO> getAll(@RequestHeader("Authorization") String token){
        Account account = parseUsername(token);
        return favoritesService.getAllFavorites(account);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Integer> id) throws ObjectNotFoundException, ObjectNotCreatedException {
        Account account = parseUsername(token);
        favoritesService.add(id.get("id"), account);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestHeader("Authorization") String token,
                                             @PathVariable("id") int id) throws AccessException, ObjectNotFoundException {
        Account account =parseUsername(token);
        favoritesService.deleteApartment(id, account);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private Account parseUsername(String token){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7));
        return accountDetailsService.loadAccountByUsername(username);
    }

    @ExceptionHandler
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
