package com.example.Football_Fan.fanProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FanProfileRepository extends JpaRepository<FanProfile, Integer> {
    Optional<FanProfile> findFanProfileByPersonId(Integer personId);
}
