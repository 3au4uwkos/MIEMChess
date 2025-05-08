package io.github._au4uwkos.chess_game.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final ReactiveAuthenticationManager manager;

    @Autowired
    public SecurityConfig(ReactiveAuthenticationManager manager) {
        this.manager = manager;
    }

    // TODO Fix authorization with JWT
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/",
                                "/index.html",
                                "/static/**",
                                "/asset-manifest.json",
                                "/favicon.ico",
                                "/manifest.json",
                                "/figures/**",
                                "/img/**",
                                "/fonts/**"
                        ).permitAll()
                        .pathMatchers("/").permitAll()
                        .pathMatchers("/api/login", "/api/signup").permitAll()
                        .anyExchange().permitAll()
                )
                .authenticationManager(manager);
        return http.build();
    }
}