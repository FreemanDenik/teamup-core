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
public class MainController {
    /**
     * Стартовая страница
     * @return страница с предложением зарегистрироваться или авторизоваться.
     */
    @GetMapping(value = "")
    public String printWelcomePage() {
        return "welcome";
    }
    /**
     * @return  переход на страницу для пользователя с ролью USER
     */
    @GetMapping(value = "/user")
    public String printUserPage() {
        return "user";
    }
    /**
     * @return переход на страницу для пользователя с ролью ADMIN
     */
    @GetMapping(value = "/admin")
    public String printAdminPage() {
        return "admin";
    }
    /**
     * @return переход на страницу для пользователя с ролью MODERATOR
     */
    @GetMapping(value = "/moderator")
    public String printModeratorPage() {
        return "moderator";
    }

    /**
     * метод для перехода на страницу регистрации
     * @param model - сущность для передачи юзера в html
     * @return  страница с формой регистрации
     */
    @GetMapping(value = "/registration")
    public String printRegistrationPage (Model model){
        model.addAttribute ("user",new User ());
        return "registration";
    }

    /**
     * необходимо отредактировать этот метод для проверки, регистрации и сохранения
     * нового пользователя в БД
     * @param model - сущность для обмена информацией между методом и html.
     * @param user - юзер, желающий зарегистрироваться.
     * @return В случае успешной регистрации нового пользователя переход на страницу авторизации,
     * в ином случае - registration.html
     */
    @PostMapping(value = "/registration")
    public String registrationNewUser(Model model, @ModelAttribute("user") User user){
        if (1==1){
            return "registration";
        }else {
           return "redirect:/login";
        }
    }
}

