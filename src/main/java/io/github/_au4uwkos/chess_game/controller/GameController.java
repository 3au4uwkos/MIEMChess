package io.github._au4uwkos.chess_game.controller;

import io.github._au4uwkos.chess_game.service.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    private final MatchmakingService matchmakingService;

    @Autowired
    public GameController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping("/begin")
    public Mono<ResponseEntity<String>> beginGame() {
        String username = "some";
        return matchmakingService.addToQueue(username)
                .map(match -> ResponseEntity.ok(
                        String.format("{\"status\":\"success\",\"opponent\":\"%s\",\"color\":\"%s\"}",
                                match.getOpponentUsername(),
                                match.getColor())
                ));
    }
}
