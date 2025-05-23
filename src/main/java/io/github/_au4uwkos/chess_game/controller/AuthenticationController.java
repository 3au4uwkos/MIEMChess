package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.security.JWT.JwtCore;
import io.github._au4uwkos.chess_game.service.UserService;
import io.github._au4uwkos.chess_game.transfer.AuthRequestTransfer;
import io.github._au4uwkos.chess_game.transfer.AuthResponseTransfer;
import io.github._au4uwkos.chess_game.transfer.TokenTransfer;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtCore generator;

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequestTransfer authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .flatMap(userDetails -> {
                    if (new BCryptPasswordEncoder().matches(authRequest.getPassword(), userDetails.getPassword())) {
                        String token = generator.generateToken(userDetails.getUsername());
                        return Mono.just(ResponseEntity.ok(new AuthResponseTransfer(token, userDetails.getUsername())));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Invalid password"));
                    }
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("User not found"))
                        .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Login error: " + e.getMessage()))));
    }

    @PostMapping("/api/signup")
    public Mono<ResponseEntity<?>> signup(@RequestBody UserEntity user) {
        return userService.existsByUsername(user.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(ResponseEntity
                                .badRequest()
                                .body("Username already exists"));
                    } else {
                        user.encodePassword();
                        return userService.save(user)
                                .map(savedUser -> ResponseEntity.ok(
                                        new AuthResponseTransfer(generator.generateToken(user.getUsername()), user.getUsername())
                                ));
                    }
                });
    }

    @PostMapping("/api/validate")
    public Mono<ResponseEntity<?>> validate(@RequestBody TokenTransfer token) {
        System.out.println("Received auth header: " + token.getToken());
        return Mono.fromCallable(() -> {

            String key = token.getToken();
            if (!generator.validateToken(key)) {
                System.out.println("Not a valid token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            System.out.println("Validated");
            return ResponseEntity.ok(generator.getName(key));
        }).onErrorResume(e -> {
            // Логирование ошибки при необходимости
            System.out.println("Error: " + e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        });
    }


    @GetMapping("/api/protected")
    public Mono<ResponseEntity<String>> protectedEndpoint() {
        return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!"));
    }
}