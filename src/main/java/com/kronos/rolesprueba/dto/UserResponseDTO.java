package com.kronos.rolesprueba.dto;

import com.kronos.rolesprueba.model.User;

public record UserResponseDTO(
        Long id,
        String username,
        boolean enabled
        // TODO: agregar los roles, retira el UserResponseDTO del controlador y servicio y veras como vienen los roles
) {
    public UserResponseDTO(User user) {
        this(
                user.getUserId(),
                user.getUsername(),
                user.isEnabled()
        );
    }
}
