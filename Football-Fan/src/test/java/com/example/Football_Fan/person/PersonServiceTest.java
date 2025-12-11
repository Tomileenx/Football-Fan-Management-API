package com.example.Football_Fan.person;


import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.fan.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    // which service we want to test
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSuccessfullyCreateAPerson() {
        // Given
        PersonDto personDto = new PersonDto(
                "Tomi",
                "Wemimo",
                "tw@gmail.com",
                1
        );
        Person person = new Person(
                "John",
                "Doe",
                "jd@gmail.com",
                22
        );
        Person savedPerson = new Person(
                "John",
                "Doe",
                "jd@gmail.com",
                22
        );
        savedPerson.setId(1);

        // Mock the calls
        when(personMapper.toPerson(personDto))
                .thenReturn(person);
        when(personRepository.save(person))
                .thenReturn(savedPerson);
        when(personMapper.toResponseDto(savedPerson))
                .thenReturn(new PersonResponseDto(
                        "Tomi",
                        "Wemimo",
                        "tw@gmail.com",
                        "Arsenal"
                ));

        // When
        PersonResponseDto responseDto = personService.create(personDto);

        // Then
        assertEquals(personDto.firstname(), responseDto.firstname());
        assertEquals(personDto.lastname(), responseDto.lastname());
        assertEquals(personDto.email(), responseDto.email());
        assertEquals("Arsenal", responseDto.clubName());

        verify(personMapper, times(1))
                .toPerson(personDto);
        verify(personRepository, times(1))
                .save(person);
        verify(personMapper, times(1))
                .toResponseDto(savedPerson);
    }

    @Test
    public void shouldSuccessfullyFindAllPerson() {
        // Given
        List<Person> persons = new ArrayList<>();
        persons.add(
                new Person(
                        "John",
                        "Doe",
                        "Jd@gmail.com",
                        23
                )
        );

        // Mock the calls
        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.toResponseDto(any(Person.class)))
                .thenReturn(
                        new PersonResponseDto(
                                "John",
                                "Doe",
                                "Jd@gmail.com",
                                "Arsenal"
                        )
                );

        // When
        List<PersonResponseDto> responseDtoList = personService.getAllPersons();

        // Then
        assertEquals(persons.size(), responseDtoList.size());
        verify(personRepository, times(1)).findAll();
        verify(personMapper, times(persons.size()))
                .toResponseDto(any(Person.class));
    }

    @Test
    public void shouldSuccessfullyFindPersonById() {
        // Given
        Integer personId = 1;
        Person person = new Person(
                "John",
                "Doe",
                "Jd@gmail.com",
                23
        );

        // Mock the calls
        when(personRepository.findById(personId))
                .thenReturn(Optional.of(person));
        when(personMapper.toResponseDto(any(Person.class)))
                .thenReturn(new PersonResponseDto(
                        "John",
                        "Doe",
                        "Jd@gmail.com",
                        "Arsenal"
                ));


        // When
        PersonResponseDto responseDto = personService.getPersonById(personId);

        // Then
        assertEquals(person.getFirstname(), responseDto.firstname());
        assertEquals(person.getLastname(), responseDto.lastname());
        assertEquals(person.getEmail(), responseDto.email());
        assertEquals("Arsenal", responseDto.clubName());

        verify(personRepository, times(1))
                .findById(personId);
        verify(personMapper, times(1))
                .toResponseDto(person);
    }

    @Test
    public void shouldSuccessfullyDeletePersonById() {
        // Given
        Integer personId = 1;

        // When
        personService.deletePersonById(personId);

        // Then
        verify(personRepository, times(1))
                .deleteById(personId);
    }
}
