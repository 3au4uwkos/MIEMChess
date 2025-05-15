package io.github._au4uwkos.chess_game.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github._au4uwkos.chess_game.processor.field.Coordinates;
import io.github._au4uwkos.chess_game.processor.field.WebGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.UUID;

@Component
public class GameWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        logger.info("New WebSocket connection: {}", session.getId());

        // Создаем новую игру для одного игрока
        WebGame game = new WebGame(session.getId(), session.getId(), UUID.randomUUID().toString(), null);

        return session.receive()
                .takeUntil(_ -> false)
                .flatMap(message -> processMove(message, game, session))
                .then();
    }

    private Mono<Void> processMove(WebSocketMessage message, WebGame game, WebSocketSession session) {
        if (message.getType() != WebSocketMessage.Type.TEXT) {
            return Mono.empty();
        }

        String payload = message.getPayloadAsText();
        logger.info("Received move: {}", payload);

        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            if (!"move".equals(jsonNode.get("type").asText())) {
                return Mono.empty();
            }

            int fromRow = jsonNode.get("from").get("row").asInt();
            int fromCol = jsonNode.get("from").get("col").asInt();
            int toRow = jsonNode.get("to").get("row").asInt();
            int toCol = jsonNode.get("to").get("col").asInt();

            Coordinates from = new Coordinates(fromRow, fromCol);
            Coordinates to = new Coordinates(toRow, toCol);

            // Обрабатываем ход и отправляем обновленные доступные ходы
            return game.processMove(from, to)
                    .then(Mono.fromCallable(game::availableMovesToJSON))
                    .flatMap(availableMoves -> session.send(
                            Mono.just(session.textMessage(
                                    String.format("{\"type\":\"moves\",\"data\":%s}", availableMoves)
                            ))
                    ));
        } catch (Exception e) {
            logger.error("Error processing move", e);
            return session.send(Mono.just(session.textMessage(
                    "{\"type\":\"error\",\"message\":\"Invalid move\"}"
            )));
        }
    }
}