package io.github._au4uwkos.chess_game.security;


import io.github._au4uwkos.chess_game.model.Role;
import io.github._au4uwkos.chess_game.model.UserEntity;
import io.github._au4uwkos.chess_game.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
