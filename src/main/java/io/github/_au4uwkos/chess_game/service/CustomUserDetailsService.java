package io.github._au4uwkos.chess_game.service;


import io.github._au4uwkos.chess_game.repository.RoleRepository;
import io.github._au4uwkos.chess_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .flatMap(user -> roleRepository.findRolesByUserId(user.getId()) // Получаем Mono<List<Role>>
                        .map(roles -> roles.stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName())) // Преобразуем роли в GrantedAuthority
                                .collect(Collectors.toList()) // Собираем список
                        )
                        .map(grantedAuthorities -> new User(user.getUsername(), user.getPassword(), grantedAuthorities)) // Создаем UserDetails
                );
    }

}
