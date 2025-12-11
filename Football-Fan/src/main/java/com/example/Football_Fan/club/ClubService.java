package com.example.Football_Fan.club;

import com.example.Football_Fan.fan.PersonDto;
import com.example.Football_Fan.fan.PersonMapper;
import com.example.Football_Fan.fan.PersonRepository;
import com.example.Football_Fan.fan.PersonResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public ClubService(ClubRepository clubRepository, ClubMapper clubMapper, PersonRepository personRepository, PersonMapper personMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public ClubDto postClub(ClubDto dto) {
        var club = clubMapper.toClub(dto);
        clubRepository.save(club);
        return dto;
    }

    public List<ClubDto> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(clubMapper::toClubDto)
                .collect(Collectors.toList());
    }

    public List<ClubDto> getClubByName(String name) {
        return clubRepository.findClubByName(name)
                .stream()
                .map(clubMapper::toClubDto)
                .collect(Collectors.toList());
    }

    public List<PersonResponseDto> getClubPersons(String name) {
        return personRepository.findByClubName(name)
                .stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
