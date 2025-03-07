package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.model.Role;
import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.repository.RoleRepository;
import io.github._au4uwkos.chess_game.repository.UserRepository;
import io.github._au4uwkos.chess_game.security.JWTGenerator;
import io.github._au4uwkos.chess_game.transfer.AuthResponseTransfer;
import io.github._au4uwkos.chess_game.transfer.LoginTransfer;
import io.github._au4uwkos.chess_game.transfer.RegisterTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder,
                                    JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseTransfer> login(@RequestBody LoginTransfer loginTransfer) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginTransfer.getUsername(),
                        loginTransfer.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseTransfer(token), HttpStatus.OK);

    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterTransfer registerTransfer) {
        if (userRepository.existsByUsername(registerTransfer.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerTransfer.getUsername());
        user.setPassword(passwordEncoder.encode((registerTransfer.getPassword())));

        @SuppressWarnings("OptionalGetWithoutIsPresent") Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User register successful!", HttpStatus.OK);
    }
}
