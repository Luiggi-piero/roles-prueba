package com.kronos.rolesprueba.controller;

import com.kronos.rolesprueba.dto.UserAuthenticationDataDTO;
import com.kronos.rolesprueba.infra.security.DataJWTToken;
import com.kronos.rolesprueba.infra.security.TokenService;
import com.kronos.rolesprueba.model.User;
import com.kronos.rolesprueba.service.IPasswordValidationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IPasswordValidationService passwordValidationService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenService tokenService,
                                    IPasswordValidationService passwordValidationService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordValidationService = passwordValidationService;
    }

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDataDTO userAuthenticationDataDTO){
        passwordValidationService.validatePassword(new String(userAuthenticationDataDTO.password()));
        // Este token encapsula el nombre de usuario y contraseña que se desean autenticar.
        // Aún no se ha verificado si son correctos; simplemente es una representación de los datos
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationDataDTO.username(), userAuthenticationDataDTO.password());
        // Este metodo verifica las credenciales contra el sistema de autenticación configurado
        //  (por ejemplo, una base de datos, memoria, etc.).
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new DataJWTToken(JWTToken));
    }
}
