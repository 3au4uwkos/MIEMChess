package io.github._au4uwkos.chess_game.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;


@RestController
public class TestController {
    @Value("${spring.application.name}")
    private String name;

    @GetMapping
    public String getNameApplication() {
        return name;
    }
}
