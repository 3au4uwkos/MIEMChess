package io.github._au4uwkos.chess_game.repository;

import io.github._au4uwkos.chess_game.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    <S extends Role> Optional<S> findByName(String name);
}
