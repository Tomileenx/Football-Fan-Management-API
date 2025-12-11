package com.example.Football_Fan.fan;


import com.example.Football_Fan.club.ClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, ClubRepository clubRepository) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PersonResponseDto create(
            PersonDto dto
    ) {
        var person = personMapper.toPerson(dto);
        var savedPerson = personRepository.save(person);
        return personMapper.toResponseDto(savedPerson);
    }

    public List<PersonResponseDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PersonResponseDto getPersonById(Integer id) {
        return personRepository.findById(id)
                .map(personMapper::toResponseDto)
                .orElse(null);
    }

    public void deletePersonById(Integer id) {
        personRepository.deleteById(id);
    }
}
