package com.example.Football_Fan.users;

import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.exception.UserAlreadyExistsException;
import com.example.Football_Fan.jwt.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AppUserService(AppUserRepo appUserRepo, AppUserMapper appUserMapper, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.appUserRepo = appUserRepo;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AppUserResponseDto register(AppUserDto dto) {
        appUserRepo.findUsersByEmail(dto.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(
                            "user with email " + dto.email() + " already exists."
                    );
                });

        // Map DTO to AppUser
        AppUser user = appUserMapper.toAppUser(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.ROLE_USER);

        // Save user
        appUserRepo.save(user);

        // Generate Jwt token
        String jwtToken = jwtService.generateToken(user);

        return new AppUserResponseDto(
                jwtToken,
                "User registered successfully"
        );
    }

    public AppUserResponseDto login(AppUserDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        var user = appUserRepo.findUsersByEmail(dto.email())
                .orElseThrow(() -> new UsernameNotFoundException(dto.email()));

        var jwtToken = jwtService.generateToken(user);

        return new AppUserResponseDto(
                jwtToken,
                "Login successful"
        );
    }

    public void changeEmail(AppUser user, String newEmail) {
        if (appUserRepo.findUsersByEmail(newEmail).isPresent()) {
            throw new EntityExistsException("Email already in use");
        }
        user.setEmail(newEmail);
        appUserRepo.save(user);
    }

    public void changePassword(AppUser user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        appUserRepo.save(user);
    }

    @PreAuthorize("hasROLE('ADMIN')")
    public AppUserResponseDto registerAdmin(AppUserDto dto) {
        appUserRepo.findUsersByEmail(dto.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Admin with email " + dto.email() + " already exists.");
                });

        // Map DTO to AppUser
        AppUser user = appUserMapper.toAppUser(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.ROLE_ADMIN);

        // Save user
        appUserRepo.save(user);

        // Generate Jwt token
        String jwtToken = jwtService.generateToken(user);

        return new AppUserResponseDto(
                jwtToken,
                "Admin registered successfully"
        );
    }

    public void deleteUser(AppUser user) {
        appUserRepo.delete(user);
    }
}
