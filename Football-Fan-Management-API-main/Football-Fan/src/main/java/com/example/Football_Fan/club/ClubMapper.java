package com.example.Football_Fan.club;

import org.springframework.stereotype.Service;

@Service
public class ClubMapper {
    public Club toClub(ClubDto dto) {
        return new Club(dto.name());
    }

    public ClubDto toClubDto(Club club) {
        return new ClubDto(club.getName());
    }
}
