package io.github._au4uwkos.chess_game.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

@Table("user_roles")
@Data
public class UserRole {

    @Id
    private Integer id;

    private Integer userId;
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

