package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FanProfileMapper {

    public FanProfile toFan(FanProfileDto dto) {
        FanProfile fanProfile = new FanProfile();

        fanProfile.setAge(dto.age());
        fanProfile.setNationality(dto.nationality());
        fanProfile.setDateJoined(LocalDate.now());

        return fanProfile;
    }

    public void updateFanProfileFromDto(
            FanProfileDto dto, FanProfile fanProfile
    ) {
        fanProfile.setAge(dto.age());
        fanProfile.setNationality(dto.nationality());
    }

    public FanProfileResponseDto toFanProfileResponseDto(FanProfile fanProfile) {
        return new FanProfileResponseDto(
                fanProfile.getPerson().getFirstname(),
                fanProfile.getPerson().getLastname(),
                fanProfile.getPerson().getAppUser() != null ? fanProfile.getPerson().getAppUser().getEmail() : null,
                fanProfile.getAge(),
                fanProfile.getPerson().getClub() != null ? fanProfile.getPerson().getClub().getName() : null,
//                fanProfile.getPerson().getClub().getName(),
                fanProfile.getNationality(),
                fanProfile.getDateJoined()
        );
    }
}
