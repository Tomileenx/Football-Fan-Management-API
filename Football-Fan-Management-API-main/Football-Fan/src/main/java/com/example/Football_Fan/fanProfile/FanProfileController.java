package com.example.Football_Fan.fanProfile;


import com.example.Football_Fan.users.AppUser;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FanProfileController {

    private final FanProfileService fanProfileService;

    public FanProfileController(FanProfileService fanProfileService) {
        this.fanProfileService = fanProfileService;
    }

    @PostMapping("/user/profile")
    public FanProfileResponseDto post(
            @Valid @RequestBody FanProfileDto dto,
            @AuthenticationPrincipal AppUser user
            ) {
        return this.fanProfileService.postFanProfile(dto, user);
    }

    @GetMapping("/user/profile")
    public FanProfileResponseDto getMyFanProfile(@AuthenticationPrincipal AppUser user) {
        return fanProfileService.getMyFanProfile(user);
    }

    @PutMapping("/user/profile/update")
    public FanProfileResponseDto updateMyFanProfile(
            @Valid @RequestBody FanProfileDto dto,
            @AuthenticationPrincipal AppUser user
    ) {
        return fanProfileService.updateMyFanProfile(dto, user);
    }

    @GetMapping("/admin/profiles")
    public List<FanProfileResponseDto> getAllProfiles() {
        return fanProfileService.getAllFansProfile();
    }

    @GetMapping("/admin/profiles/{personId}")
    public FanProfileResponseDto getFanProfileByPersonId(
            @PathVariable Integer personId
    ) {
        return fanProfileService.getFanProfileByPersonId(personId);
    }
}
