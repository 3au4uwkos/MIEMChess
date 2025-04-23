package io.github._au4uwkos.chess_game.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Table("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private int id;

    private String username;

    private String password;

    @MappedCollection(idColumn = "user_id")
    private List<UserRole> roles= new ArrayList<>();

    public void setPassword(String password){
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
