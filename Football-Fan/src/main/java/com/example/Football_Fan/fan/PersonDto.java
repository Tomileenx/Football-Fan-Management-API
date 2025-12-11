package com.example.Football_Fan.fan;

import jakarta.validation.constraints.NotEmpty;

public record PersonDto(

        @NotEmpty(message = "Firstname cannot be empty" )
    String firstname,

        @NotEmpty(message = "Lastname cannot be empty")
    String lastname,


        @NotEmpty(message = "Email cannot be empty")
    String email,

    Integer clubId
) {
}
