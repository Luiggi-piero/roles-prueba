package com.kronos.rolesprueba.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDataDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
