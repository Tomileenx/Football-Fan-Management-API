package com.example.Football_Fan.club;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    List<Club> findClubByName(String name);
}
