package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.security.JWTUtil;
import io.github._au4uwkos.chess_game.service.UserService;
import io.github._au4uwkos.chess_game.transfer.AuthRequestTransfer;
import io.github._au4uwkos.chess_game.transfer.AuthResponseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public Mono<ResponseEntity<AuthResponseTransfer>> login(@RequestBody AuthRequestTransfer authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .<ResponseEntity<AuthResponseTransfer>>handle((userDetails, sink) -> {
                    if (userDetails.getPassword().equals(authRequest.getPassword())) {
                        sink.next(ResponseEntity.ok(new AuthResponseTransfer(jwtUtil.generateToken(authRequest.getUsername()))));
                    } else {
                        sink.error(new BadCredentialsException("Invalid username or password"));
                    }
                }).switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }
    @PostMapping("/api/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody UserEntity user) {
        // Encrypt password before saving
        user.setPassword(user.getPassword());
        return userService.save(user)
                .map(_ -> ResponseEntity.ok("User signed up successfully"));
    }

    @GetMapping("/api/protected")
    public Mono<ResponseEntity<String>> protectedEndpoint() {
        return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!"));
    }
}