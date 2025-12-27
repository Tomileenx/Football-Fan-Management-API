package com.example.Football_Fan.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeUserEmailDto(
     @NotBlank(message = "Email cannot be empty")
     @Email(message = "Email should be valid")
     String newEmail
) {
}
