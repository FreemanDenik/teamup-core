package ru.team.up.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.team.up.auth.oauth2.CustomOAuth2User;
import ru.team.up.auth.service.UserServiceAuth;
import ru.team.up.auth.service.impl.UserDetailsImpl;
import ru.team.up.auth.service.impl.UserServiceAuthImpl;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

/**
 * Контроллер для регистрации, авторизации
 * и переадресации на страницы в зависимости от роли пользователя
 */
@Controller
public class MainController {

    private final UserServiceAuth userServiceAuth;
    private final UserDetailsImpl userDetails;

    @Autowired
    public MainController(UserServiceAuth userServiceAuth, UserDetailsImpl userDetails) {
        this.userServiceAuth = userServiceAuth;
        this.userDetails = userDetails;
    }

    /**
     * Стартовая страница
     *
     * @return страница с предложением зарегистрироваться или авторизоваться.
     */
    @GetMapping(value = "")
    public String printWelcomePage() {
        return "welcome";
    }

    /**
     * @return переход на страницу для пользователя с ролью USER
     */
    @GetMapping(value = "/user")
    public String printUserPage(Model model) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedUser", account);
        return "user";
    }

    /**
     * @return переход на страницу для пользователя с ролью ADMIN
     */
    @GetMapping(value = "/admin")
    public String printAdminPage(Model model) {
        String email = ((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = (Account) userDetails.loadUserByUsername(email);
        model.addAttribute("loggedUser", account);
        return "admin";
    }

    /**
     * @return переход на страницу для пользователя с ролью MODERATOR
     */
    @GetMapping(value = "/moderator")
    public String printModeratorPage(Model model) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedUser", account);
        return "moderator";
    }

    /**
     * метод для перехода на страницу регистрации
     *
     * @param model - сущность для передачи юзера в html
     * @return страница с формой регистрации
     */
    @GetMapping(value = "/registration")
    public String printRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * необходимо отредактировать этот метод для проверки, регистрации и сохранения
     * нового пользователя в БД
     *
     * @param model - сущность для обмена информацией между методом и html.
     * @param user  - юзер, желающий зарегистрироваться.
     * @return В случае успешной регистрации нового пользователя переход на страницу авторизации,
     * в ином случае - registration.html
     */
    @PostMapping(value = "/registration")
    public String registrationNewUser(@ModelAttribute User user, Model model) {
        userServiceAuth.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        return "login";
    }
}

