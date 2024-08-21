package com.vtjaeger.payment.dtos;

import com.vtjaeger.payment.models.User;

public record UserRequest(
        String name,
        String email,
        String password) {

    public User toModel() {
        return new User(name, email, password);
    }
}
