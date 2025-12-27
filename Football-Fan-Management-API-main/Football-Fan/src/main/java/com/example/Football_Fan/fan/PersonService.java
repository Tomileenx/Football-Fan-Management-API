package com.example.Football_Fan.fan;


import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.club.ClubDto;
import com.example.Football_Fan.club.ClubRepository;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final ClubRepository clubRepository;
    private final PersonMapper personMapper;
    private final AppUserRepo appUserRepo;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, ClubRepository clubRepository, AppUserRepo appUserRepo) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.clubRepository = clubRepository;
        this.appUserRepo = appUserRepo;
    }

    public PersonResponseDto create(
            PersonDto dto, AppUser user
    ) {

        if (user.getPerson() != null) {
            throw new IllegalStateException("User already has a person created");
        }

        // Map DTO to Person
        Person person = personMapper.toPerson(dto);

        // Fetch and assign the chosen Club
        Club club = clubRepository.findById(dto.clubId())
                .orElseThrow(() -> new EntityNotFoundException("club not found"));

        person.setClub(club);

        // Assign ownership to user
        person.setAppUser(user);
        user.setPerson(person);
        user.setClub(club);

        // Save person
        personRepository.save(person);
        appUserRepo.save(user);

        return personMapper.toResponseDto(person);
    }

    public PersonResponseDto getMyPerson(AppUser user) {
        Person person = user.getPerson();

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        return personMapper.toResponseDto(person);
    }

    public PersonResponseDto updateMyPerson(PersonDto dto, AppUser user) {
        Person person = user.getPerson();

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        personMapper.updatePersonFromDto(dto, person);

        return personMapper.toResponseDto(person);
    }

    public PersonResponseDto updateMyClub(ClubDto dto, AppUser user) {
        Person person = user.getPerson();

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        // Fetch and update the chosen club
        Club club = clubRepository.findClubByName(dto.name())
                .orElseThrow(() -> new EntityNotFoundException("club not found"));

        // update club
        person.setClub(club);
        user.setClub(club);

        return personMapper.toResponseDto(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PersonResponseDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PersonResponseDto getPersonById(Integer id) {
        return personRepository.findById(id)
                .map(personMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }
}
