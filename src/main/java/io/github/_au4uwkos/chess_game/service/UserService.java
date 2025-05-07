package io.github._au4uwkos.chess_game.service;


import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<Boolean> existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public Mono<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<UserEntity> save(UserEntity user) {
        return userRepository.save(user);
    }
}
