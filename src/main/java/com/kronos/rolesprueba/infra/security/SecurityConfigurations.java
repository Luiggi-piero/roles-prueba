package com.kronos.rolesprueba.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Este metodo expone un AuthenticationManager como un bean en tu aplicación Spring,
     * utilizando la configuración predeterminada proporcionada por Spring Security.
     * - AuthenticationManager: Es el componente central de Spring Security encargado de autenticar usuarios.
     * Cuando alguien intenta iniciar sesión, este manager
     * valida las credenciales usando los UserDetailsService, PasswordEncoder, etc.
     * - En versiones modernas de Spring Security (Spring Boot 2.7+ y 3.x), ya no se configura automáticamente un
     * AuthenticationManager accesible como bean. Por eso, si quieres inyectarlo en
     * otros componentes (como en un servicio que genera tokens JWT), debes exponerlo así manualmente.
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * - Declara un bean de tipo PasswordEncoder que usa el algoritmo BCrypt para codificar y verificar
     * contraseñas en tu aplicación Spring Security.
     * - PasswordEncoder es una interfaz de Spring Security que:
     * Codifica contraseñas antes de guardarlas en la base de datos.
     * Verifica contraseñas ingresadas comparándolas con la codificada (por ejemplo, durante el login).
     * - Debes inyectarlo donde codifiques contraseñas, por ejemplo en servicios que crean usuarios.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
