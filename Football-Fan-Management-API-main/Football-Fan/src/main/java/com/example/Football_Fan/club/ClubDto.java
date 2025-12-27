package com.example.Football_Fan.club;

import jakarta.validation.constraints.NotBlank;

public record ClubDto(
        @NotBlank(message = "Club name must be provided")
        String name
) {
}
