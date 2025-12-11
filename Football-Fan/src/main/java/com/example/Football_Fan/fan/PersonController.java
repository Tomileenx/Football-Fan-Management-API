package com.example.Football_Fan.fan;


import com.example.Football_Fan.club.Club;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/person")
    public PersonResponseDto create(
           @Valid @RequestBody PersonDto dto
    ) {
        return this.personService.create(dto);
    }

    @GetMapping("/persons")
    public List<PersonResponseDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/{person-id}")
    public PersonResponseDto getPersonById(
            @PathVariable("person-id") Integer id
    ) {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/persons/{person-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePersonById(
            @PathVariable("person-id") Integer id
    ) {
        personService.deletePersonById(id);
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
