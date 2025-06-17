package com.kronos.rolesprueba.service;

import com.kronos.rolesprueba.dto.UserRegisterRequestDTO;
import com.kronos.rolesprueba.dto.UserResponseDTO;
import com.kronos.rolesprueba.infra.exceptions.DuplicateResourceException;
import com.kronos.rolesprueba.model.Role;
import com.kronos.rolesprueba.model.RolesEnum;
import com.kronos.rolesprueba.model.User;
import com.kronos.rolesprueba.repository.IRoleRepository;
import com.kronos.rolesprueba.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IPasswordValidationService passwordValidationService;
    private final IRoleRepository roleRepository;

    public UserService(IUserRepository userRepository,
                       IPasswordValidationService passwordValidationService,
                       IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordValidationService = passwordValidationService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<UserResponseDTO> getUsers(Pageable pagination) {
        return userRepository.findAll(pagination).map(UserResponseDTO::new);
    }

    @Transactional
    @Override
    public void createUser(UserRegisterRequestDTO userDto) {
        validateUserUniqueness(userDto);
        passwordValidationService.validatePassword(new String(userDto.password()));
        User user = new User(userDto);
        assignDefaultRole(user);
        userRepository.save(user);
    }

    private void validateUserUniqueness(UserRegisterRequestDTO userDto) {
        if (userRepository.existsByUsername(userDto.email())) {
            throw new DuplicateResourceException("El correo electrónico ya existe");
        }
    }

    private void assignDefaultRole(User user) {
        Role role = roleRepository.findByName(RolesEnum.USER);
        user.getRoles().add(role);
    }
}
