package io.github._au4uwkos.chess_game.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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

    @Column("username")
    @Size(min = 3, max = 50)
    private String username;

    @Column("password")
    private String password;

    public void encodePassword(){
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }
}
