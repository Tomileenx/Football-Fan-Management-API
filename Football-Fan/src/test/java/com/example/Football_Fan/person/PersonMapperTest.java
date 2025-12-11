package com.example.Football_Fan.person;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.club.ClubRepository;
import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonDto;
import com.example.Football_Fan.fan.PersonMapper;
import com.example.Football_Fan.fan.PersonResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonMapperTest {

    @Mock
    private ClubRepository clubRepository;


    private PersonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PersonMapper(clubRepository);
    }

    @Test
    public void shouldMapPersonDtoToPerson() {
        // Given
        PersonDto dto = new PersonDto(
                "Tomi",
                "Wemimo",
                "tw@gmail.com",
                1
        );

        Club club = new Club("Arsenal");
        club.setId(1);

        // Mock the calls
        when(clubRepository.findById(1))
                .thenReturn(Optional.of(club));

        // When
        Person person = mapper.toPerson(dto);

        assertEquals(dto.firstname(), person.getFirstname());
        assertEquals(dto.lastname(), person.getLastname());
        assertEquals(dto.email(), person.getEmail());
        assertEquals(club, person.getClub());

        verify(clubRepository, times(1)).findById(1);
    }

    @Test
    public void shouldThrowExceptionWhenClubNotFound() {
        // Arrange
        when(clubRepository.findById(5))
                .thenReturn(Optional.empty());

        // Act
        var exp = assertThrows(RuntimeException.class, () -> mapper.toPerson(new PersonDto(
                "Tosin",
                "John",
                "tj@gmail.com",
                5
        )));

        // Assert
        assertEquals("Club not found", exp.getMessage());
        verify(clubRepository, times(1)).findById(5);
    }

    @Test
    public void shouldMapPersonToPersonResponseDto() {
        // Given
        Person person = new Person(
                "Tomi",
                "Doe",
                "td@gmail.com",
                23
        );


        Club club = new Club("Arsenal");
        club.setId(1);

        person.setClub(club);

        // When
        PersonResponseDto responseDto = mapper.toResponseDto(person);

        // Then
        assertEquals(person.getFirstname(), responseDto.firstname());
        assertEquals(person.getLastname(), responseDto.lastname());
        assertEquals(person.getEmail(), responseDto.email());
        assertEquals(person.getClub().getName(), responseDto.clubName());
    }
}
