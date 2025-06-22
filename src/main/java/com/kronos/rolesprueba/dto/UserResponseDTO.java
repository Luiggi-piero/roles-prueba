package com.kronos.rolesprueba.dto;

import com.kronos.rolesprueba.model.Role;
import com.kronos.rolesprueba.model.User;

import java.util.Set;

public record UserResponseDTO(
        Long id,
        String username,
        boolean enabled,
        Set<Role> roles
        // TODO: agregar los roles, retira el UserResponseDTO del controlador y servicio y veras como vienen los roles
) {
    public UserResponseDTO(User user) {
        this(
                user.getUserId(),
                user.getUsername(),
                user.isEnabled(),
                user.getRoles()
        );
    }
}
