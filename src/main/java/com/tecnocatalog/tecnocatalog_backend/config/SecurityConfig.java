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
                        // Login y registro públicos
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login",
                                "/api/usuarios/registro"
                        ).permitAll()

                        // Consulta pública del catálogo
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos",
                                "/api/productos/**",
                                "/api/categorias",
                                "/api/categorias/**"
                        ).permitAll()

                        // Favoritos: usuarios autenticados y administradores
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/usuarios/*/favoritos"
                        ).hasAnyAuthority("ROLE_ID_1", "ROLE_ID_2")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/usuarios/*/favoritos/*"
                        ).hasAnyAuthority("ROLE_ID_1", "ROLE_ID_2")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/usuarios/*/favoritos/*"
                        ).hasAnyAuthority("ROLE_ID_1", "ROLE_ID_2")

                        // Solo administradores pueden gestionar estos recursos
                        .requestMatchers("/api/productos/**").hasAuthority("ROLE_ID_1")
                        .requestMatchers("/api/categorias/**").hasAuthority("ROLE_ID_1")
                        .requestMatchers("/api/roles/**").hasAuthority("ROLE_ID_1")
                        .requestMatchers("/api/usuarios/**").hasAuthority("ROLE_ID_1")

                        .anyRequest().authenticated()
                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
