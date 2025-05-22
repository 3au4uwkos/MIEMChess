package io.github._au4uwkos.chess_game.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchmakingService {

    private final Queue<String> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Sinks.Many<MatchPair> matchSink = Sinks.many().multicast().directBestEffort();

    public Mono<MatchPair> addToQueue(String username) {
        return Mono.fromCallable(() -> {
                    waitingQueue.add(username);
                    checkMatches();
                    return username;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(this::awaitMatch);
    }

    private void checkMatches() {
        while (waitingQueue.size() >= 2) {
            String player1 = waitingQueue.poll();
            String player2 = waitingQueue.poll();

            String whitePlayer = Math.random() < 0.5 ? player1 : player2;
            assert whitePlayer != null;
            String blackPlayer = whitePlayer.equals(player1) ? player2 : player1;

            matchSink.tryEmitNext(new MatchPair(whitePlayer, blackPlayer));
        }
    }

    private Mono<MatchPair> awaitMatch(String username) {
        return matchSink.asFlux()
                .filter(pair -> pair.contains(username))
                .next();
    }

    public static class MatchPair {
        private final String whiteUsername;
        private final String blackUsername;

        public MatchPair(String whiteUsername, String blackUsername) {
            this.whiteUsername = whiteUsername;
            this.blackUsername = blackUsername;
        }

        public boolean contains(String username) {
            return whiteUsername.equals(username) || blackUsername.equals(username);
        }

        public String getOpponentUsername(String currentUsername) {
            return whiteUsername.equals(currentUsername) ? blackUsername : whiteUsername;
        }

        public String getColor(String currentUsername) {
            return whiteUsername.equals(currentUsername) ? "white" : "black";
        }

        public String getOpponentUsername() {
            return blackUsername; // Для упрощения в примере
        }

        public String getColor() {
            return whiteUsername.equals(blackUsername) ? "white" : "black"; // Для упрощения
        }
    }
}