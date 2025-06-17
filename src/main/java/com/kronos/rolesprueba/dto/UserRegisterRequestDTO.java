package com.kronos.rolesprueba.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
//        @NotBlank
//        @Size(min = 2, max = 10)
//        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        @Size(min = 8, max = 15, message = "La contraseña debe tener entre 8 y 15 caracteres")
        String password
) {
}
