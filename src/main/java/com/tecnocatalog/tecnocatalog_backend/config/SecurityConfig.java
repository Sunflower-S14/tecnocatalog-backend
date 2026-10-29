package com.tecnocatalog.tecnocatalog_backend.config;

import com.tecnocatalog.tecnocatalog_backend.security.JwtAuthenticationFilter;
import com.tecnocatalog.tecnocatalog_backend.security.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // Registro e inicio de sesión de endpoints públicos
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login",
                                "/api/usuarios/registro"
                        ).permitAll()

                        // Consulta pública del catálogos
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos",
                                "/api/productos/**",
                                "/api/categorias",
                                "/api/categorias/**"
                        ).permitAll()

                        // Solo el administrador gestiona el inventario
                        .requestMatchers("/api/productos/**")
                        .hasAuthority("ROLE_ID_1")

                        .requestMatchers("/api/categorias/**")
                        .hasAuthority("ROLE_ID_1")

                        // Roles y usuarios: administración exclusiva
                        .requestMatchers("/api/roles/**")
                        .hasAuthority("ROLE_ID_1")

                        .requestMatchers("/api/usuarios/**")
                        .hasAuthority("ROLE_ID_1")

                        // Cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
