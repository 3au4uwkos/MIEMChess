package io.github._au4uwkos.chess_game.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Component
public class GameWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        logger.info("New WebSocket connection: {}", session.getId());

        return session.receive()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(message -> {
                    if (message.getType() == WebSocketMessage.Type.TEXT) {
                        String payload = message.getPayloadAsText();
                        logger.info("Received message: {}", payload);

                        try {
                            JsonNode jsonNode = objectMapper.readTree(payload);
                            String messageType = jsonNode.get("type").asText();

                            switch (messageType) {
                                case "move":
                                    // Handle chess move
                                    int fromRow = jsonNode.get("from").get("row").asInt();
                                    int fromCol = jsonNode.get("from").get("col").asInt();
                                    int toRow = jsonNode.get("to").get("row").asInt();
                                    int toCol = jsonNode.get("to").get("col").asInt();
                                    logger.info("Move: from ({}, {}) to ({}, {})", fromRow, fromCol, toRow, toCol);
                                    // TODO: Implement game logic update and broadcast to other players
                                    break;
                                // Add other cases for different message types (e.g., chat)
                                default:
                                    logger.warn("Unknown message type: {}", messageType);
                                    break;
                            }
                        } catch (IOException e) {
                            logger.error("Error parsing message: {}", payload, e);
                        } catch (NullPointerException e) {
                            logger.error("Missing fields in message: {}", payload, e);
                        }
                    }
                })
                .then()
                .doOnTerminate(() -> logger.info("Connection closed: {}", session.getId()));
    }
}