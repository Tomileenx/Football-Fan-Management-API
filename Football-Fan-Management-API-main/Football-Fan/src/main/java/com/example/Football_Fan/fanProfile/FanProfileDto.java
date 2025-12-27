package com.example.Football_Fan.fanProfile;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record FanProfileDto(

        @Min(value = 1, message = "Age must be positive")
        int age,

        String nationality
) {
}
