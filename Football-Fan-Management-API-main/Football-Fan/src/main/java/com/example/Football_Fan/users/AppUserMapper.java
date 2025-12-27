package com.example.Football_Fan.users;

import org.springframework.stereotype.Service;

@Service
public class AppUserMapper {

    public AppUser toAppUser(AppUserDto dto) {

        AppUser appUser = new AppUser();

        appUser.setEmail(dto.email());
        appUser.setPassword(dto.password());

        return appUser;
    }
}
