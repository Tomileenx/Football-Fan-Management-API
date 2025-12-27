package com.example.Football_Fan.fan;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.club.ClubRepository;
import org.springframework.stereotype.Service;


@Service
public class PersonMapper {

    public Person toPerson(PersonDto dto) {

        var person = new Person();
        person.setFirstname(dto.firstname());
        person.setLastname(dto.lastname());

        return person;
    }

    public void updatePersonFromDto(PersonDto dto, Person person) {
        person.setFirstname(dto.firstname());
        person.setLastname(dto.lastname());
    }

    public PersonResponseDto toResponseDto(Person person) {
        return new PersonResponseDto(
                person.getFirstname(),
                person.getLastname(),
                person.getAppUser() != null ? person.getAppUser().getEmail() : null,
                person.getClub() != null ? person.getClub().getName() : null
//                person.getAppUser().getEmail(),
//                person.getClub().getName()
        );
    }
}
