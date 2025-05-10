package io.github._au4uwkos.chess_game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class GameWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        logger.info("New WebSocket connection: {}", session.getId());

        return session.receive()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(message -> {
                    if (message.getType() == WebSocketMessage.Type.TEXT) {
                        String payload = message.getPayloadAsText();
                        logger.info("Received message: {}", payload);

                        // Эхо-ответ (для теста)
                        session.send(Mono.just(session.textMessage("Echo: " + payload)))
                                .subscribe();
                    }
                })
                .then()
                .doOnTerminate(() -> logger.info("Connection closed: {}", session.getId()));
    }
}
