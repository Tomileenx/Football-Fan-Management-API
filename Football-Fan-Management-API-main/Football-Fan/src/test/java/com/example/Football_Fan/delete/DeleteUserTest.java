package com.example.Football_Fan.delete;

import com.example.Football_Fan.enumFolder.Role;
import com.example.Football_Fan.fan.Person;
import com.example.Football_Fan.fan.PersonRepository;
import com.example.Football_Fan.fanProfile.FanProfile;
import com.example.Football_Fan.fanProfile.FanProfileRepository;
import com.example.Football_Fan.users.AppUser;
import com.example.Football_Fan.users.AppUserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeleteUserTest {

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FanProfileRepository fanProfileRepository;

    @Test
    public void deleteUserShouldSuccessfullyDeletePersonAndProfile() {
        // Given
        AppUser appUser = new AppUser(
                "tomi@gmail.com",
                "delete",
                Role.ROLE_USER
        );

        Person person = new Person(
                "Tomi",
                "Person"
        );

        FanProfile fanProfile = new FanProfile(
                15,
                "Nigerian",
                LocalDate.of(2025, 12, 11)
        );

        appUser.setPerson(person);
        person.setAppUser(appUser);

        appUser.setFanProfile(fanProfile);
        fanProfile.setAppUser(appUser);

        // save
        appUserRepo.saveAndFlush(appUser);

        // sanity check
        assertEquals(1, appUserRepo.count());
        assertEquals(1, personRepository.count());
        assertEquals(1, fanProfileRepository.count());

        // When
        appUserRepo.delete(appUser);
        appUserRepo.flush();

        // Then
        assertEquals(0, appUserRepo.count());
        assertEquals(0, personRepository.count());
        assertEquals(0, fanProfileRepository.count());
    }
}
