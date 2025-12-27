package com.example.Football_Fan.users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public AppUserResponseDto register(@Valid @RequestBody AppUserDto dto) {
        return appUserService.register(dto);
    }

    @PostMapping("/login")
    public AppUserResponseDto login(@Valid @RequestBody AppUserDto dto) {
        return appUserService.login(dto);
    }

    @PutMapping("/user/email/me")
    public void changeEmail(
            @Valid @RequestBody ChangeUserEmailDto dto,
            @AuthenticationPrincipal AppUser user
    ) {
        appUserService.changeEmail(user, dto.newEmail());
    }

    @PutMapping("/user/password/me")
    public void changePassword(
            @Valid @RequestBody ChangeUserPasswordDto dto,
            @AuthenticationPrincipal AppUser user
    ) {
        appUserService.changePassword(user, dto.oldPassword(), dto.newPassword());
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AppUserResponseDto> createAdmin(@Valid @RequestBody AppUserDto dto) {
        AppUserResponseDto response = appUserService.registerAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/delete/me")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal AppUser user) {
        appUserService.deleteUser(user);
        return ResponseEntity.ok("User deleted successfully");
    }
}
