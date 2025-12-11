package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.fan.PersonRepository;
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
import static org.mockito.Mockito.*;

public class FanProfileServiceTest {

    @InjectMocks
    private  FanProfileService fanProfileService;

    @Mock
    private FanProfileRepository fanProfileRepository;

    @Mock
    private FanProfileMapper fanProfileMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSuccessfullyPostFanProfile() {
        // Given
        FanProfileDto fanProfileDto = new FanProfileDto(
                23,
                "Nigerian",
                1
        );

        FanProfile fanProfile = new FanProfile(
                24,
                "Nigerian",
                LocalDate.of(2025, 12, 11)
        );

        FanProfile savedFanProfile = new FanProfile(
                24,
                "Nigerian",
                LocalDate.of(2025, 12, 11)
        );

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
        when(fanProfileRepository.save(fanProfile))
                .thenReturn(savedFanProfile);
        when(fanProfileMapper.toFanProfileResponseDto(savedFanProfile))
                .thenReturn(expectedResponse);

        // When
        FanProfileResponseDto responseDto =
                fanProfileService.postFanProfile(fanProfileDto);

        // Then
        assertEquals(expectedResponse.age(), responseDto.age());
        assertEquals(expectedResponse.nationality(), responseDto.nationality());
        assertEquals(expectedResponse.dateJoined(), responseDto.dateJoined());
        assertEquals(expectedResponse.personFirstName(), responseDto.personFirstName());
        assertEquals(expectedResponse.personLastName(), responseDto.personLastName());
        assertEquals(expectedResponse.personEmail(), responseDto.personEmail());
        assertEquals(expectedResponse.personClub(), responseDto.personClub());

        verify(fanProfileMapper, times(1))
                .toFan(fanProfileDto);
        verify(fanProfileRepository, times(1))
                .save(fanProfile);
        verify(fanProfileMapper, times(1))
                .toFanProfileResponseDto(savedFanProfile);
    }

    @Test
    public void shouldSuccessfullyFindAllFansProfiles() {
        // Given
        List<FanProfile> fanProfiles = new ArrayList<>();
        fanProfiles.add(
                new FanProfile(
                        23,
                        "Nigerian",
                        LocalDate.of(2025,12, 11)
                )
        );

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
        when(fanProfileRepository.findAll())
                .thenReturn(fanProfiles);
        when(fanProfileMapper.toFanProfileResponseDto(any(FanProfile.class)))
                .thenReturn(expectedResponse);

        // When
        List<FanProfileResponseDto> responseDto =
                fanProfileService.getAllFansProfile();

        // Then
        assertEquals(fanProfiles.size(), responseDto.size());

        verify(fanProfileRepository, times(1))
                .findAll();
        verify(fanProfileMapper, times(fanProfiles.size()))
                .toFanProfileResponseDto(any(FanProfile.class));
    }

    @Test
    public void shouldSuccessfullyFindProfileByPersonId() {
        // Given
        Integer personId = 1;
        FanProfile fanProfile = new FanProfile(
                23,
                "Nigerian",
                LocalDate.of(2025, 12, 11)
        );

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
        when(fanProfileRepository.findFanProfileByPersonId(personId))
                .thenReturn(Optional.of(fanProfile));
        when(fanProfileMapper.toFanProfileResponseDto(any(FanProfile.class)))
                .thenReturn(expectedResponse);

        // When
        FanProfileResponseDto responseDto = fanProfileService.getFanProfileByPersonId(personId);

        // Then
        assertEquals(expectedResponse.personFirstName(), responseDto.personFirstName());
        assertEquals(expectedResponse.personLastName(), responseDto.personLastName());
        assertEquals(expectedResponse.personEmail(), responseDto.personEmail());
        assertEquals(expectedResponse.personClub(), responseDto.personClub());
        assertEquals(expectedResponse.age(), responseDto.age());
        assertEquals(expectedResponse.nationality(), responseDto.nationality());
        assertEquals(expectedResponse.dateJoined(), responseDto.dateJoined());

        verify(fanProfileRepository, times(1))
                .findFanProfileByPersonId(personId);
        verify(fanProfileMapper, times(1))
                .toFanProfileResponseDto(any(FanProfile.class));
    }
}