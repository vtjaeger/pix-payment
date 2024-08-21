package com.vtjaeger.payment.dtos;

public record UserResponse(
        Long id,
        String name,
        String email,
        String password
) {
}
