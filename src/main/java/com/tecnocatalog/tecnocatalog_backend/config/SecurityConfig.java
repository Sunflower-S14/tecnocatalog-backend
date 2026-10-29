package com.tecnocatalog.tecnocatalog_backend.config;

import com.tecnocatalog.tecnocatalog_backend.security.JwtAuthenticationFilter;
import com.tecnocatalog.tecnocatalog_backend.security.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    // Configuración CORS para permitir la comunicación con Angular
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://127.0.0.1:4200"
        ));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization", "Content-Type", "Accept"
        ));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // Permitir las solicitudes previas de CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

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
                        .requestMatchers("/api/productos/**")
                        .hasAuthority("ROLE_ID_1")

                        .requestMatchers("/api/categorias/**")
                        .hasAuthority("ROLE_ID_1")

                        .requestMatchers("/api/roles/**")
                        .hasAuthority("ROLE_ID_1")

                        .requestMatchers("/api/usuarios/**")
                        .hasAuthority("ROLE_ID_1")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
