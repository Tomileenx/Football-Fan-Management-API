package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FanProfileMapperTest {

    @Mock
    private PersonRepository personRepository;

    private FanProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FanProfileMapper(personRepository);
    }

    @Test
    public void shouldMapFanProfileDtoToFanProfile() {
        // Given
        FanProfileDto fanProfileDto = new FanProfileDto(
                23,
                "Nigerian",
                1
        );

        Person person = new Person(
                "John",
                "Doe",
                "Jd@gmail.com",
                23
        );
        person.setId(1);

        // Mock the calls
        when(personRepository.findById(1))
                .thenReturn(Optional.of(person));

        // When
        FanProfile fanProfile = mapper.toFan(fanProfileDto);

        // Then
        assertEquals(fanProfileDto.age(), fanProfile.getAge());
        assertEquals(fanProfileDto.nationality(), fanProfile.getNationality());
        assertEquals(person, fanProfile.getPerson());

        verify(personRepository, times(1))
                .findById(1);
    }

    @Test
    public void shouldThrowExceptionWhenPersonIdNotFound() {
        // Arrange
        when(personRepository.findById(1))
                .thenReturn(Optional.empty());

        // Test
        var exp = assertThrows(RuntimeException.class, () -> mapper.toFan(
                new FanProfileDto(
                        23,
                        "Nigerian",
                        1
                )
        ));

        // Assert
        assertEquals("Person not found", exp.getMessage());
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    public void shouldMapFanProfileToFanResponseDto() {
        // Given
        FanProfile fanProfile = new FanProfile(
                23,
                "Nigerian",
                LocalDate.of(2025, 11, 12)
        );

        Person person = new Person(
                "John",
                "Doe",
                "Jd@gmail.com",
                23
        );

        person.setId(1);

        // Add a club so mapping does not break
        Club club = new Club("Arsenal");
        club.setId(1);
        person.setClub(club);

        fanProfile.setPerson(person);

        // When
        FanProfileResponseDto fanProfileResponseDto =
                mapper.toFanProfileResponseDto(fanProfile);

        // Then
        assertEquals(fanProfile.getAge(), fanProfileResponseDto.age());
        assertEquals(fanProfile.getNationality(), fanProfileResponseDto.nationality());
        assertEquals(fanProfile.getDateJoined(), fanProfileResponseDto.dateJoined());
        assertEquals(club.getName(), fanProfileResponseDto.personClub());
    }
}
