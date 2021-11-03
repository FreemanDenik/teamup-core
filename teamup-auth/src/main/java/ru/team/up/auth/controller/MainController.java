package ru.team.up.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @GetMapping(value = "/user")
    public String printUserPage() {
        return "user";
    }
    @GetMapping(value = "/admin")
    public String printAdminPage() {
        return "admin";
    }
    @GetMapping(value = "/moderator")
    public String printModeratorPage() {
        return "moderator";
    }
    @GetMapping(value = "/welcome")
    public String printWelcomePage() {
        return "welcome";
    }
}
