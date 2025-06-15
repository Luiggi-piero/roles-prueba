package com.kronos.rolesprueba.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kronos.rolesprueba.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}") // nombre de la propiedad definida en application.properties
    private String apiSecret;

    public String generateToken(User user){
        try {
            Algorithm algorithm =  Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Skill Link") // Emitido por, nombre cualquiera
                    .withSubject(user.getUsername()) // Dirigido a?
                    .withClaim("id", user.getUserId()) // guarda el id del usuario
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    private Instant generateExpirationDate(){
        // retorna la fecha de expiracion, Retorna ese Instant, que representa la fecha y hora de expiración en UTC.
        /**
         * .toInstant(ZoneOffset.of("-05:00"))
         * Convierte ese LocalDateTime a un objeto Instant (marca de tiempo UTC), usando la zona horaria -05:00.
         * Por ejemplo:
         * - 2025-06-14T18:30:00-05:00 en zona horaria -5
         * - Se transforma en Instant equivalente: 2025-06-14T23:30:00Z (en UTC)
         *
         * italia: +02:00
         */
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));

        /*
        * return ZonedDateTime.now(ZoneId.of("Europe/Rome"))
                        .plusHours(2)
                        .toInstant();
        * */
    }

    // Obtener el nombre del usuario
    public String getSubject(String token){
        DecodedJWT verifier = null;
        if(token == null) {
            throw new RuntimeException();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Skill Link")
                    .build()
                    .verify(token);
            verifier.getSubject();
        }catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }

        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier inválido");
        }
        return verifier.getSubject();
    }
}
