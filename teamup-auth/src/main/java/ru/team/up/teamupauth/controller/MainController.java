package ru.team.up.teamupauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {

        return "login";
    }
}
