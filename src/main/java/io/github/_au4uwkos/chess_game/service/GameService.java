package io.github._au4uwkos.chess_game.service;

import io.github._au4uwkos.chess_game.processor.field.WebGame;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, WebGame> activeGames = new ConcurrentHashMap<>();
    private final Map<String, Sinks.Many<String>> gameSinks = new ConcurrentHashMap<>();

    public Mono<Void> registerGame(String gameId, WebGame game) {
        return Mono.fromRunnable(() -> {
            activeGames.put(gameId, game);
            gameSinks.put(gameId, Sinks.many().multicast().directBestEffort());
        });
    }

    public Mono<Void> unregisterGame(String gameId) {
        return Mono.fromRunnable(() -> {
            activeGames.remove(gameId);
            Sinks.Many<String> sink = gameSinks.remove(gameId);
            if (sink != null) {
                sink.tryEmitComplete();
            }
        });
    }

    public Mono<WebGame> getGame(String gameId) {
        return Mono.justOrEmpty(activeGames.get(gameId));
    }

    public Mono<Void> sendMessage(String gameId, String sessionId, String messageType, String payload) {
        return Mono.fromRunnable(() -> {
            Sinks.Many<String> sink = gameSinks.get(gameId);
            if (sink != null) {
                String formattedMessage = String.format("{\"type\":\"%s\",\"target\":\"%s\",\"payload\":%s}",
                        messageType, sessionId, payload);
                sink.tryEmitNext(formattedMessage);
            }
        });
    }

    public Mono<Void> broadcast(String gameId, String messageType, String payload) {
        return Mono.fromRunnable(() -> {
            Sinks.Many<String> sink = gameSinks.get(gameId);
            if (sink != null) {
                String formattedMessage = String.format("{\"type\":\"%s\",\"payload\":%s}",
                        messageType, payload);
                sink.tryEmitNext(formattedMessage);
            }
        });
    }

    public Flux<String> getGameMessages(String gameId) {
        return Flux.defer(() -> {
            Sinks.Many<String> sink = gameSinks.computeIfAbsent(gameId,
                    k -> Sinks.many().multicast().directBestEffort());
            return sink.asFlux();
        });
    }
}
