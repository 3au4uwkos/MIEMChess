package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.processor.field.Game;
import io.github._au4uwkos.chess_game.security.JWT.JwtCore;
import io.github._au4uwkos.chess_game.service.GameService;
import io.github._au4uwkos.chess_game.service.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GameController {

    private final MatchmakingService matchmakingService;
    private final JwtCore jwtCore;

    @Autowired
    public GameController(MatchmakingService matchmakingService, JwtCore jwtCore) {
        this.matchmakingService = matchmakingService;
        this.jwtCore = jwtCore;
    }

    @PostMapping("/game")
    public Mono<ResponseEntity<Map<String, Object>>> beginGame(
            @RequestParam(value = "game") int game,
            @RequestParam(value = "username") String username
    ) {

        return Mono.defer(() -> {
            if (game == 1) {
                // Добавление в очередь
                return matchmakingService.addToQueue(username)
                        .map(match -> ResponseEntity.ok().body(createSuccessResponse("added", match,username)))
                        .onErrorResume(e -> Mono.just(
                                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(createErrorResponse("Failed to add to queue"))
                        ));
            } else if (game == 0) {
                // Удаление из очереди
                return matchmakingService.removeFromQueue(username)
                        .map(removed -> {
                            if (removed) {
                                return ResponseEntity.ok().body(createSuccessResponse("removed", null, username));
                            } else {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(createErrorResponse("User not found in queue"));
                            }
                        });
            } else {
                return Mono.just(
                        ResponseEntity.badRequest()
                                .body(createErrorResponse("Invalid game parameter (must be 0 or 1)"))
                );
            }
        });
    }

    private Map<String, Object> createSuccessResponse(String action, MatchmakingService.MatchPair match, String username) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("action", action);
        if (match != null) {
            response.put("match", Map.of(
                    "opponent", match.getOpponentUsername(username),
                    "color", match.getIsWhite(username) ? "white" : "black"
            ));
        }
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        return Map.of(
                "status", "error",
                "message", message
        );
    }
}
