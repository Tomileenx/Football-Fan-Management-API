package com.example.Football_Fan.fan;

import com.example.Football_Fan.club.ClubDto;
import jakarta.validation.constraints.NotEmpty;

public record PersonDto(

        @NotEmpty(message = "Firstname cannot be empty" )
    String firstname,

        @NotEmpty(message = "Lastname cannot be empty")
    String lastname,


    Integer clubId
) {
}
