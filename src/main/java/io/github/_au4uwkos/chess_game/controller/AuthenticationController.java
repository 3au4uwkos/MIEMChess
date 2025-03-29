package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.model.Role;
import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.model.UserRole;
import io.github._au4uwkos.chess_game.repository.RoleRepository;
import io.github._au4uwkos.chess_game.repository.UserRepository;
import io.github._au4uwkos.chess_game.security.JWTGenerator;
import io.github._au4uwkos.chess_game.transfer.AuthResponseTransfer;
import io.github._au4uwkos.chess_game.transfer.LoginTransfer;
import io.github._au4uwkos.chess_game.transfer.RegisterTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public AuthenticationController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JWTGenerator jwtGenerator
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponseTransfer>> login(@RequestBody LoginTransfer loginTransfer) {
        return userRepository.findByUsername(loginTransfer.getUsername())
                .flatMap(user -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user.getUsername(), loginTransfer.getPassword()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = jwtGenerator.generateToken(authentication);
                    return Mono.just(ResponseEntity.ok(new AuthResponseTransfer(token)));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> register(@RequestBody RegisterTransfer registerTransfer) {
        return userRepository.existsByUsername(registerTransfer.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is taken!"));
                    }

                    UserEntity user = new UserEntity();
                    user.setUsername(registerTransfer.getUsername());
                    user.setPassword(passwordEncoder.encode(registerTransfer.getPassword()));

                    return roleRepository.findByName("USER")
                            .flatMap(role -> {

                                UserRole userRole = new UserRole(user.getId(), role.getId());

                                user.setRoles(Collections.singletonList(userRole)); // Теперь это List<UserRole>
                                return userRepository.save(user);
                            })
                            .thenReturn(ResponseEntity.ok("User register successful!"));
                });
    }
}

