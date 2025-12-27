package com.example.Football_Fan.config;

import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final AppUserRepo appUserRepo;

    public ApplicationConfig(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> appUserRepo.findUsersByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("username not found"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AppUserRepo appUserRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (appUserRepo.findUsersByEmail("admin@example.com").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setRole(Role.ROLE_ADMIN);

                appUserRepo.save(admin);

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("Admin user created: admin@example.com, Admin@123");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        };
    }
}
