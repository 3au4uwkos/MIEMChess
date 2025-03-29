package io.github._au4uwkos.chess_game.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("roles")

public class Role {

    @Id
    private int id;

    private String name;
}
