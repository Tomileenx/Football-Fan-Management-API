package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonRepository;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FanProfileServiceTest {

    @InjectMocks
    private  FanProfileService fanProfileService;

    @Mock
    private FanProfileRepository fanProfileRepository;

    @Mock
    private FanProfileMapper fanProfileMapper;

    @Mock
    private AppUserRepo appUserRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSuccessfullyPostFanProfile() {
        // Given
        FanProfileDto fanProfileDto = new FanProfileDto(
                15,
                "Ghanian"
        );

        FanProfile fanProfile = new FanProfile(
                24,
                "Nigerian",
                LocalDate.of(2025, 12, 11)
        );

        Person existingPerson = new Person("Existing", "Individual");
        Club club = new Club("Arsenal");

        existingPerson.setClub(club);

        fanProfile.setPerson(existingPerson);

        AppUser appUser = new AppUser(
                "tomi@gmail.com",
                "create",
                Role.ROLE_USER
        );

        appUser.setPerson(existingPerson);
        existingPerson.setAppUser(appUser);

        FanProfileResponseDto expectedResponse = new FanProfileResponseDto
                (
                        "John",
                        "Doe",
                        "Jd@gmail.com",
                        23,
                        "Arsenal",
                        "Nigerian",
                        LocalDate.of(2025, 12, 11)
        );

        // Mock the calls
        when(fanProfileMapper.toFan(fanProfileDto))
                .thenReturn(fanProfile);
        when(fanProfileMapper.toFanProfileResponseDto(fanProfile))
                .thenReturn(expectedResponse);
        when(fanProfileRepository.save(fanProfile))
                .thenReturn(fanProfile);
        when(appUserRepo.save(appUser))
                .thenReturn(appUser);

        // When
        FanProfileResponseDto responseDto =
                fanProfileService.postFanProfile(fanProfileDto, appUser);

        // Then
        assertEquals(expectedResponse.age(), responseDto.age());
        assertEquals(expectedResponse.nationality(), responseDto.nationality());
        assertEquals(expectedResponse.dateJoined(), responseDto.dateJoined());
        assertEquals(expectedResponse.personFirstName(), responseDto.personFirstName());
        assertEquals(expectedResponse.personLastName(), responseDto.personLastName());
        assertEquals(expectedResponse.AppUserEmail(), responseDto.AppUserEmail());
        assertEquals(expectedResponse.personClub(), responseDto.personClub());

        verify(fanProfileMapper, times(1))
                .toFan(fanProfileDto);
        verify(fanProfileRepository, times(1))
                .save(fanProfile);
        verify(fanProfileMapper, times(1))
                .toFanProfileResponseDto(fanProfile);
        verify(appUserRepo, times(1))
                .save(appUser);
    }

    @Test
    public void shouldFailToCreateFanProfileWhenFanProfileExists() {
        FanProfileDto fanProfileDto = new FanProfileDto(
                15,
                "Ghanian"
        );

        AppUser appUser = new AppUser(
                "tomi@gmail.com",
                "create",
                Role.ROLE_USER
        );

        Person existingPerson = new Person("Existing", "Individual");
        Club club = new Club("Arsenal");

        existingPerson.setClub(club);
        appUser.setPerson(existingPerson);
        existingPerson.setAppUser(appUser);

        FanProfile existingFanProfile = new FanProfile();
        appUser.setFanProfile(existingFanProfile);

        assertThrows(
                EntityExistsException.class,
                () -> fanProfileService.postFanProfile(fanProfileDto, appUser)
        );

        verify(fanProfileRepository, never()).save(any());
        verify(appUserRepo, never()).save(any());
    }
}