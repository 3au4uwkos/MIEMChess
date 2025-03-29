package io.github._au4uwkos.chess_game.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table("users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private int id;

    private String username;

    private String password;

    @MappedCollection(idColumn = "user_id")
    private List<UserRole> roles= new ArrayList<>();
}
