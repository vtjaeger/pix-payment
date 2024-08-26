package com.vtjaeger.payment.dtos;

import com.vtjaeger.payment.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "name cannot be null")
        String name,
        @NotBlank(message = "email cannot be null")
        @Email
        String email,
        @NotBlank(message = "password cannot be null")
        @Size(min = 8, message = "password must have 8 characters")
        String password,
        @NotBlank(message = "role cannot be null")
        String role) {

    public User toModel() {
        return new User(name, email, password, role);
    }
}
