package com.example.Football_Fan.club;

import com.example.Football_Fan.fan.PersonDto;
import com.example.Football_Fan.fan.PersonMapper;
import com.example.Football_Fan.fan.PersonRepository;
import com.example.Football_Fan.fan.PersonResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    public ClubDto postClub(ClubDto dto) {

        if (clubRepository.findClubByName(dto.name()).isPresent()) {
            throw new EntityExistsException("Club with this name already exists");
        }

        var club = clubMapper.toClub(dto);
        clubRepository.save(club);
        return clubMapper.toClubDto(club);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ClubDto> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(clubMapper::toClubDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    ClubDto getClubByName(String name) {
        Club club = clubRepository.findClubByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Club not found"));
        return clubMapper.toClubDto(club);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PersonResponseDto> getClubPersons(String name) {
        return personRepository.findByClubName(name)
                .stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
