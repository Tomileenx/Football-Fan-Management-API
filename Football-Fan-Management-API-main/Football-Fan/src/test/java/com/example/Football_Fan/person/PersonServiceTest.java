package com.example.Football_Fan.person;


import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.club.ClubRepository;
import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.exception.UserAlreadyExistsException;
import com.example.Football_Fan.fan.*;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    // which service we want to test
    @InjectMocks
    private PersonService personService;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AppUserRepo appUserRepo;

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
                1
        );

        Club club = new Club("Arsenal");
        club.setId(1);

        Person person = new Person(
                "Tomi",
                "Wemimo"
        );

        AppUser appUser = new AppUser(
                "tomi@gmail.com",
                "create",
                Role.ROLE_USER
        );

        PersonResponseDto expectedResponse = new PersonResponseDto(
                "Tomi",
                "Wemimo",
                "tomi@gmail.com",
                "Arsenal"
        );

        // Mock the calls
        when(personMapper.toPerson(personDto))
                .thenReturn(person);
        when(personMapper.toResponseDto(person))
                .thenReturn(expectedResponse);
        when(clubRepository.findById(1))
                .thenReturn(Optional.of(club));
        when(personRepository.save(person))
                .thenReturn(person);
        when(appUserRepo.save(appUser))
                .thenReturn(appUser);

        // When
        PersonResponseDto responseDto = personService.create(personDto, appUser);

        // Then
        assertEquals(expectedResponse.firstname(), responseDto.firstname());
        assertEquals(expectedResponse.lastname(), responseDto.lastname());
        assertEquals(expectedResponse.AppUserEmail(), responseDto.AppUserEmail());
        assertEquals(expectedResponse.clubName(), responseDto.clubName());


        verify(personMapper, times(1))
                .toPerson(personDto);
        verify(clubRepository, times(1))
                .findById(1);
        verify(personMapper, times(1))
                .toResponseDto(person);
        verify(personRepository, times(1))
                .save(person);
        verify(appUserRepo, times(1))
                .save(appUser);
    }


    @Test
    public void shouldFailToCreatePersonWhenPersonExists() {
        // Given
        PersonDto personDto = new PersonDto(
                "Tomi",
                "Wemimo",
                1
        );

        AppUser appUser = new AppUser(
                "tomi@gmail.com",
                "create",
                Role.ROLE_USER
        );

        Person existingPerson = new Person("Existing", "Person");
        appUser.setPerson(existingPerson);

        assertThrows(
                IllegalStateException.class,
                () -> personService.create(personDto, appUser)
        );

        verify(personRepository, never()).save(any());
        verify(appUserRepo, never()).save(any());
        verify(clubRepository, never()).findById(any());
    }
}