package com.example.Football_Fan.club;


import com.example.Football_Fan.fan.PersonResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/admin/club")
    public ClubDto postClub(
            @RequestBody ClubDto dto
    ) {
        return clubService.postClub(dto);
    }

    @GetMapping("/admin/clubs")
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/admin/clubs/search/{club-name}")
    public ClubDto getClubByName (
            @PathVariable("club-name") String name
    ) {
        return clubService.getClubByName(name);
    }

   @GetMapping("/admin/clubs/persons/{club-name}")
   public List<PersonResponseDto> getClubPersons(
           @PathVariable("club-name") String name
   ) {
       return clubService.getClubPersons(name);
   }
}
