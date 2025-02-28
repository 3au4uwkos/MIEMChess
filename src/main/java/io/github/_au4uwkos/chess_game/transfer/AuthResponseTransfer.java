package io.github._au4uwkos.chess_game.transfer;

import lombok.Data;

@Data
public class AuthResponseTransfer {

    private String accessToken;
    private final String tokenType = "Bearer ";

    public AuthResponseTransfer(String accessToken) {
        this.accessToken = accessToken;
    }

}
