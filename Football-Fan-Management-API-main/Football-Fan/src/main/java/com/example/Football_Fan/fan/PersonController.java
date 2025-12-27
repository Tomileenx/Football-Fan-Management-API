package com.example.Football_Fan.fan;



import com.example.Football_Fan.club.ClubDto;
import com.example.Football_Fan.users.AppUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/user/person")
    public PersonResponseDto create(
           @Valid @RequestBody PersonDto dto,
           @AuthenticationPrincipal AppUser user
    ) {
        return this.personService.create(dto, user);
    }

    @GetMapping("/user/person")
    public PersonResponseDto getMyPerson(@AuthenticationPrincipal AppUser user) {
        return personService.getMyPerson(user);
    }

    @PutMapping("/user/person/update")
    public PersonResponseDto updateMyPerson(
            @Valid @RequestBody PersonDto dto,
            @AuthenticationPrincipal AppUser user
    ) {
        return personService.updateMyPerson(dto, user);
    }

    @PutMapping("/user/person/user_club")
    public PersonResponseDto updateMyClub(
            @Valid @RequestBody ClubDto dto,
            @AuthenticationPrincipal AppUser user
    ) {
        return personService.updateMyClub(dto, user);
    }

    @GetMapping("/admin/persons")
    public List<PersonResponseDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/admin/persons/{person-id}")
    public PersonResponseDto getPersonById(
            @PathVariable("person-id") Integer id
    ) {
        return personService.getPersonById(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
