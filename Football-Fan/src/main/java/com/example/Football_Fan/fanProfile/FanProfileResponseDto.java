package com.example.Football_Fan.fanProfile;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDate;

public record FanProfileResponseDto(
        String personFirstName,

        String personLastName,

        String personEmail,

        int age,

        String personClub,

        String nationality,

        LocalDate dateJoined
) {
}
