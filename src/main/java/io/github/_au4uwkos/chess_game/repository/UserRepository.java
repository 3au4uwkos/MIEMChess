package io.github._au4uwkos.chess_game.repository;

import io.github._au4uwkos.chess_game.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    <S extends UserEntity> Optional<S> findByUsername(String username);
    Boolean existsByUsername(String username);
}
