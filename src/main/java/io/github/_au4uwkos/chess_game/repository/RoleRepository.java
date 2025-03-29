package io.github._au4uwkos.chess_game.repository;

import io.github._au4uwkos.chess_game.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;


public interface RoleRepository extends ReactiveCrudRepository<Role, Integer> {

    @Query("SELECT * FROM roles WHERE name = :name")
    Mono<Role> findByName(String name);

    @Query("SELECT r.* FROM roles r INNER JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = :userId")
    Mono<List<Role>> findRolesByUserId(Integer userId);

}
