package com.example.Football_Fan.fanProfile;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FanProfileController {

    private final FanProfileService fanProfileService;

    public FanProfileController(FanProfileService fanProfileService) {
        this.fanProfileService = fanProfileService;
    }

    @PostMapping("/profile")
    public FanProfileResponseDto post(
            @RequestBody FanProfileDto dto
    ) {
        return this.fanProfileService.postFanProfile(dto);
    }

    @GetMapping("profiles")
    public List<FanProfileResponseDto> getAllProfiles() {
        return fanProfileService.getAllFansProfile();
    }

    @GetMapping("profiles/{personId}")
    public FanProfileResponseDto getFanProfileByPersonId(
            @PathVariable Integer personId
    ) {
        return fanProfileService.getFanProfileByPersonId(personId);
    }
}
