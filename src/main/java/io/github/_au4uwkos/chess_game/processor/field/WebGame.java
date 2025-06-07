package io.github._au4uwkos.chess_game.processor.field;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github._au4uwkos.chess_game.processor.figures.Figure;
import io.github._au4uwkos.chess_game.processor.figures.King;
import io.github._au4uwkos.chess_game.processor.figures.Rook;
import io.github._au4uwkos.chess_game.service.GameService;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class WebGame extends Game {

    private Coordinates whiteKing;
    private Coordinates blackKing;

    private Field field;
    private HashMap<Coordinates, Figure> figures;

    private String whitePlayerSessionID;
    private String blackPlayerSessionID;
    private boolean isWhitePlayerActive;
    private final GameService gameService;
    private final String gameId;

    public WebGame(String whitePlayerSessionID, String blackPlayerSessionID, String gameId, GameService gameService) {

        super();

        this.whitePlayerSessionID = whitePlayerSessionID;
        this.blackPlayerSessionID = blackPlayerSessionID;
        this.gameId = gameId;
        this.gameService = gameService;
        this.isWhitePlayerActive = true;
    }

    private void move(Coordinates begin, Coordinates input) {
        Figure figure = figures.get(begin);
        if (figure.getClass() == Rook.class) ((Rook) figure).setIsMoved();
        if (figure.getClass() == King.class) {
            ((King) figure).setWasUnderAttack();
            if (Math.abs(input.getPosition() - begin.getPosition()) == 2) {
                if (figure.isWhite() && begin.equals(new Coordinates(0, 4))) {
                    Figure rook;
                    if (input.getPosition() > begin.getPosition()) {
                        rook = figures.get(new Coordinates(0, 7));
                        figures.remove(rook.getCoordinates());
                        rook.setCoordinates(new Coordinates(0, 5));
                    } else {
                        rook = figures.get(new Coordinates(0, 0));
                        figures.remove(rook.getCoordinates());
                        rook.setCoordinates(new Coordinates(0, 3));
                    }
                    figures.put(rook.getCoordinates(), rook);
                } else if (!figure.isWhite() && begin.equals(new Coordinates(7, 4))) {
                    Figure rook;
                    if (input.getPosition() > begin.getPosition()) {
                        rook = figures.get(new Coordinates(7, 7));
                        figures.remove(rook.getCoordinates());
                        rook.setCoordinates(new Coordinates(7, 5));
                    } else {
                        rook = figures.get(new Coordinates(7, 0));
                        figures.remove(rook.getCoordinates());
                        rook.setCoordinates(new Coordinates(7, 3));
                    }
                    figures.put(rook.getCoordinates(), rook);
                }
            }
            if (figure.isWhite()) whiteKing = input;
            else blackKing = input;
        }
        figure.setCoordinates(input);
        figures.put(input, figure);
        figures.remove(begin);
    }

    public Mono<Void> processMove(Coordinates begin, Coordinates input) throws JsonProcessingException {
        field.checkAttack();
        Field previous = new Field(field);

        if (!isWhitePlayerActive) {
            if (field.getIsUnderBlackAttack().contains(whiteKing)) {
                if (checkmateWhite()) {
                    return gameService.broadcast(gameId, "finish", "{\"winner\":\"black\"}");
                }
                return gameService.sendMessage(gameId, blackPlayerSessionID, "return", "{\"reason\":\"check\"}");
            }
            if (field.getIsUnderWhiteAttack().contains(blackKing)) {
                return gameService.sendMessage(gameId, blackPlayerSessionID, "return", "{\"reason\":\"kingUnderAttack\"}")
                        .then(Mono.fromRunnable(() -> field = new Field(previous)));
            }
        } else {
            if (field.getIsUnderWhiteAttack().contains(blackKing)) {
                if (checkmateBlack()) {
                    return gameService.broadcast(gameId, "finish", "{\"winner\":\"white\"}");
                }
                return gameService.sendMessage(gameId, whitePlayerSessionID, "return", "{\"reason\":\"check\"}");
            }
            if (field.getIsUnderBlackAttack().contains(whiteKing)) {
                return gameService.sendMessage(gameId, whitePlayerSessionID, "return", "{\"reason\":\"kingUnderAttack\"}")
                        .then(Mono.fromRunnable(() -> field = new Field(previous)));
            }
        }

        move(begin, input);
        return switchPlayer()
                .then(gameService.sendMessage(gameId, (isWhitePlayerActive ? whitePlayerSessionID : blackPlayerSessionID), "continue", availableMovesToJSON()));

    }

    private Mono<Void> switchPlayer() {
        return Mono.fromRunnable(() -> isWhitePlayerActive = !isWhitePlayerActive);
    }

    public String availableMovesToJSON() throws JsonProcessingException {
        Map<Byte, List<Byte>> moves = new HashMap<>();
        for (Coordinates c : figures.keySet()) {
            moves.put(c.toByte(), figures.get(c).getAllowedMovements(field).stream().map(Coordinates::toByte).collect(Collectors.toList()));
        }
        return new ObjectMapper().writeValueAsString(moves);
    }
}
