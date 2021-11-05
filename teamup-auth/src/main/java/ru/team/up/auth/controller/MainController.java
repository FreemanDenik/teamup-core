package ru.team.up.auth.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.User;

/**
 * Контроллер для регистрации, авторизации
 * и переадресации на страницы в зависимости от роли пользователя
 */
@Controller
@RequestMapping("/")
public class MainController {
    /**
     * Стартовая страница
     * @return
     */
    @GetMapping(value = "")
    public String printWelcomePage() {
        return "welcome";
    }
    /**
     *  переход на страницу для пользователя с ролью USER
     * @return
     */
    @GetMapping(value = "/user")
    public String printUserPage() {
        return "user";
    }
    /**
     *  переход на страницу для пользователя с ролью ADMIN
     * @return
     */
    @GetMapping(value = "/admin")
    public String printAdminPage() {
        return "admin";
    }
    /**
     *  переход на страницу для пользователя с ролью MODERATOR
     * @return
     */
    @GetMapping(value = "/moderator")
    public String printModeratorPage() {
        return "moderator";
    }

    /**
     * метод для перехода на страницу регистрации
     * @param model
     * @return
     */
    @GetMapping(value = "/registration")
    public String printRegistrationPage (Model model){
        model.addAttribute ("user",new User ());
        return "registration";
    }

    /**
     * необходимо отредактировать этот метод для проверки,регистрации  и сохранения
     * нового пользователя в БД
     * @param model
     * @param user
     * @return
     */
    @PostMapping(value = "/registration")
    public String registrationNewUser(Model model, @ModelAttribute("user") User user){
            return "registration";
    }
}

