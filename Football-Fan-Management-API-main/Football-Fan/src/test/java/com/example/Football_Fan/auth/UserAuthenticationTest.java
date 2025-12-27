package com.example.Football_Fan.auth;

import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.exception.UserAlreadyExistsException;
import com.example.Football_Fan.jwt.JwtService;
import com.example.Football_Fan.users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserAuthenticationTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepo appUserRepo;

    @Mock
    private AppUserMapper appUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldRegisterUser() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "register"
        );

        AppUser appUser = new AppUser(
                "tomi@mail.com",
                "register",
                Role.ROLE_USER
        );

        AppUserResponseDto expectedResponse = new AppUserResponseDto(
                "jrhieshis.sknfnvnfofmlf",
                "User registered successfully"
        );

        // Mock the calls
        when(appUserRepo.findUsersByEmail("tomi@gmail.com"))
                .thenReturn(Optional.empty());
        when(appUserMapper.toAppUser(appUserDto))
                .thenReturn(appUser);
        when(passwordEncoder.encode("register"))
                .thenReturn("encoded-password");
        when(appUserRepo.save(appUser))
                .thenReturn(appUser);
        when(jwtService.generateToken(appUser))
                .thenReturn(expectedResponse.token());
        // When
        AppUserResponseDto responseDto = appUserService.register(appUserDto);

        // Then
        assertEquals(expectedResponse.token(), responseDto.token());
        assertEquals(expectedResponse.message(), responseDto.message());

        verify(appUserRepo, times(1))
                .save(appUser);
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "register"
        );

        // Mock the calls
        when(appUserRepo.findUsersByEmail("tomi@gmail.com"))
                .thenReturn(Optional.of(new AppUser()));

        assertThrows(
                UserAlreadyExistsException.class,
                () -> appUserService.register(appUserDto)
        );

        verify(appUserRepo, never()).save(any());
    }

    @Test
    public void shouldSuccessfullyLoginUser() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "login"
        );

        AppUser appUser = new AppUser(
                "tomi@mail.com",
                "register",
                Role.ROLE_USER
        );

        AppUserResponseDto expectedResponse = new AppUserResponseDto(
                "token",
                "Login successful"
        );

        // Mock the calls
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(appUserRepo.findUsersByEmail("tomi@gmail.com"))
                .thenReturn(Optional.of(appUser));
        when(jwtService.generateToken(appUser))
                .thenReturn(expectedResponse.token());

        // When
        AppUserResponseDto responseDto = appUserService.login(appUserDto);

        // Then
        assertEquals(expectedResponse.token(), responseDto.token());
        assertEquals(expectedResponse.message(), responseDto.message());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appUserRepo, times(1))
                .findUsersByEmail("tomi@gmail.com");
        verify(jwtService, times(1))
                .generateToken(appUser);
    }

    @Test
    public void shouldFailLoginWhenPasswordisWrong() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "login"
        );

        // Mock the calls
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Then
        assertThrows(
                BadCredentialsException.class,
                () -> appUserService.login(appUserDto)
        );

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(appUserRepo, never()).findUsersByEmail(any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    public void shouldSuccessfullyRegisterAdmin() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "register"
        );

        AppUser admin = new AppUser(
                "tomi@gmail.com",
                "register",
                Role.ROLE_ADMIN
        );

        AppUserResponseDto appUserResponseDto= new AppUserResponseDto(
                "jrhieshis.sknfnvnfofmlf",
                "Admin registered successfully"
        );

        // Mock the calls
        when(appUserRepo.findUsersByEmail("tomi@gmail.com"))
                .thenReturn(Optional.empty());
        when(appUserMapper.toAppUser(appUserDto))
                .thenReturn(admin);
        when(passwordEncoder.encode("register"))
                .thenReturn("register");
        when(jwtService.generateToken(admin))
                .thenReturn(appUserResponseDto.token());

        // When
        AppUserResponseDto responseDto = appUserService.registerAdmin(appUserDto);

        // Then
        assertEquals(appUserResponseDto.token(), responseDto.token());
        assertEquals(appUserResponseDto.message(), responseDto.message());

        verify(appUserRepo, times(1))
                .save(admin);
    }

    @Test
    public void shouldThrowExceptionWhenAdminAlreadyExists() {
        // Given
        AppUserDto appUserDto = new AppUserDto(
                "tomi@gmail.com",
                "register"
        );

        // Mock the calls
        when(appUserRepo.findUsersByEmail("tomi@gmail.com"))
                .thenReturn(Optional.of(new AppUser()));

        assertThrows(
                UserAlreadyExistsException.class,
                () -> appUserService.registerAdmin(appUserDto)
        );

        verify(appUserRepo, never()).save(any());
    }
}
