package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FanProfileMapper {

    private final PersonRepository personRepository;

    public FanProfileMapper(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public FanProfile toFan(FanProfileDto dto) {
        FanProfile fanProfile = new FanProfile();

        fanProfile.setAge(dto.age());
        fanProfile.setNationality(dto.nationality());
        fanProfile.setDateJoined(LocalDate.now());

        Person person = personRepository.findById(
                dto.personId())
                .orElseThrow(() -> new RuntimeException("Person not found"));

        fanProfile.setPerson(person);

        return fanProfile;
    }

    public FanProfileResponseDto toFanProfileResponseDto(FanProfile fanProfile) {
        return new FanProfileResponseDto(
                fanProfile.getPerson().getFirstname(),
                fanProfile.getPerson().getLastname(),
                fanProfile.getPerson().getEmail(),
                fanProfile.getAge(),
                fanProfile.getPerson().getClub().getName(),
                fanProfile.getNationality(),
                fanProfile.getDateJoined()
        );
    }
}
