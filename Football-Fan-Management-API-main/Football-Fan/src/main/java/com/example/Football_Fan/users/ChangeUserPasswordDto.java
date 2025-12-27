package com.example.Football_Fan.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeUserPasswordDto(
        @NotBlank
        String oldPassword,

        @NotBlank(message = "New password cannot be empty")
        @Size(min = 6, message = "New password must be at least 6 characters")
        String newPassword
) {
}
