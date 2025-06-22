package com.kronos.rolesprueba.service;

import com.kronos.rolesprueba.dto.UserRegisterRequestDTO;
import com.kronos.rolesprueba.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserResponseDTO> getUsers(Pageable pagination);

    void createUser(UserRegisterRequestDTO userDto);
}
