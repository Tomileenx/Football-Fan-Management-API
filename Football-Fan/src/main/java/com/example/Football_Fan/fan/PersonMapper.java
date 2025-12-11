package com.example.Football_Fan.fan;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.club.ClubRepository;
import org.springframework.stereotype.Service;


@Service
public class PersonMapper {

    private final ClubRepository clubRepository;

    public PersonMapper(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Person toPerson(PersonDto dto) {

        var person = new Person();
        person.setFirstname(dto.firstname());
        person.setLastname(dto.lastname());
        person.setEmail(dto.email());

        Club club = clubRepository.findById(dto.clubId())
                .orElseThrow(() -> new RuntimeException("Club not found"));

        person.setClub(club);

        return person;

//        var club = new Club();
//        club.setId(dto.clubId());
//
//        person.setClub(club);

//        return person;
    }

    public PersonResponseDto toResponseDto(Person person) {
        return new PersonResponseDto(
                person.getFirstname(),
                person.getLastname(),
                person.getEmail(),
                person.getClub().getName()
        );
    }
}
