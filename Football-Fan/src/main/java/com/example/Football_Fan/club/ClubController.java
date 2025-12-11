package com.example.Football_Fan.club;


import com.example.Football_Fan.fan.PersonResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/club")
    public ClubDto postClub(
            @RequestBody ClubDto dto
    ) {
        return clubService.postClub(dto);
    }

    @GetMapping("/clubs")
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/clubs/search/{club-name}")
    public List<ClubDto> getClubByName(
            @PathVariable("club-name") String name
    ) {
        return clubService.getClubByName(name);
    }

   @GetMapping("/clubs/persons/{club-name}")
   public List<PersonResponseDto> getClubPersons(
           @PathVariable("club-name") String name
   ) {
       return clubService.getClubPersons(name);
   }
}
