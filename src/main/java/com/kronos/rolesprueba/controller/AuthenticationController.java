package com.kronos.rolesprueba.controller;

import com.kronos.rolesprueba.dto.UserAuthenticationDataDTO;
import com.kronos.rolesprueba.infra.security.DataJWTToken;
import com.kronos.rolesprueba.infra.security.TokenService;
import com.kronos.rolesprueba.model.User;
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

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDataDTO userAuthenticationDataDTO){
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
