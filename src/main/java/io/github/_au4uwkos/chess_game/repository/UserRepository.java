package io.github._au4uwkos.chess_game.repository;

import io.github._au4uwkos.chess_game.model.UserEntity;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {
    @Query("SELECT * FROM users WHERE username = :username")
    Mono<UserEntity> findByUsername(String username);

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE username = :username)")
    Mono<Boolean> existsByUsername(String username);
}
