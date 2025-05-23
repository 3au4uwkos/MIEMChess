package io.github._au4uwkos.chess_game.transfer;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class TokenTransfer {

    private String token;

    public String getToken() {
        return token.substring(7);
    }
}
