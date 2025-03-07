package io.github._au4uwkos.chess_game.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @GetMapping("login")
    public String loginPage() {
        return "login.html";
    }

    @GetMapping("register")
    public String registerPage() {
        return "register";
    }
}
