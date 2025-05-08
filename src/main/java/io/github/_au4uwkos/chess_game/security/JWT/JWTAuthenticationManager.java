package io.github._au4uwkos.chess_game.security.JWT;

import io.github._au4uwkos.chess_game.service.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTValidator validator;
    private final UserService userService;

    public JWTAuthenticationManager(JWTValidator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(1);
        String token = authentication.getCredentials().toString();
        String username = validator.extractUsername(token);

        return userService.findByUsername(username)
                .handle((userDetails, sink) -> {
                    if (validator.validateToken(token, userDetails.getUsername())) {
                        sink.next(authentication);
                    } else {
                        sink.error(new AuthenticationException("Invalid JWT token") {
                        });
                    }
                });
    }

    public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    return Mono.just(SecurityContextHolder.getContext().getAuthentication());
                }
                return Mono.empty();
            }
        };
    }
}
