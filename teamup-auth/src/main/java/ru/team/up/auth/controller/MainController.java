package ru.team.up.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.team.up.auth.models.CustomOAuth2User;
import ru.team.up.auth.service.impl.UserDetailsImpl;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import org.springframework.security.core.Authentication;
import ru.team.up.core.repositories.ModeratorSessionRepository;
import ru.team.up.sup.service.ParameterService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


/**
 * Контроллер для регистрации, авторизации
 * и переадресации на страницы в зависимости от роли пользователя
 */
@Slf4j
@Controller
public class MainController {

    private final UserDetailsImpl userDetails;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    public MainController(UserDetailsImpl userDetails, ModeratorSessionRepository moderatorSessionRepository) {
        this.userDetails = userDetails;
    }

    private Account getCurrentAccount() {
        String email;
        if (SecurityContextHolder.getContext().getAuthentication().toString().contains("given_name")) {
            email = ((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account account = (Account) auth.getPrincipal();
            email =account.getEmail();
        }
        return (Account) userDetails.loadUserByUsername(email);
    }
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    private void autoLogin(String email, String password, HttpServletRequest request) {
        UserDetails user = userDetails.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password
                , user.getAuthorities());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            if (auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
                        , SecurityContextHolder.getContext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Account account = (Account) userDetails.loadUserByUsername(email);
        log.debug("Успешная авторизация id:{},  email:{}", account.getId(), account.getEmail());
    }


    /**
     * Стартовая страница
     *
     * @return страница с предложением зарегистрироваться или авторизоваться.
     */
    @GetMapping(value = "")
    public String printWelcomePage() {
        if (!ParameterService.printWelcomePageEnabled.getValue()) {
            log.debug("Метод printWelcomePage выключен параметром printWelcomePageEnabled = false");
            throw new RuntimeException("Method printWelcomePage is disabled by parameter printWelcomePageEnabled");
        }
        return "welcome";
    }

    /**
     * @return переход на страницу для пользователя с ролью USER
     */
    @GetMapping(value = "/user")
    public String printUserPage(Model model) {
        if (!ParameterService.printUserPageEnabled.getValue()) {
            log.debug("Метод printUserPage выключен параметром printUserPageEnabled = false");
            throw new RuntimeException("Method printUserPage is disabled by parameter printUserPageEnabled");
        }
        model.addAttribute("loggedUser", getCurrentAccount());
        return "user";
    }

    /**
     * @return переход на страницу для пользователя с ролью ADMIN
     */
    @GetMapping(value = "/admin")
    public String printAdminPage(Model model) {
        if (!ParameterService.printAdminPageEnabled.getValue()) {
            log.debug("Метод printAdminPage выключен параметром printAdminPageEnabled = false");
            throw new RuntimeException("Method printAdminPage is disabled by parameter printAdminPageEnabled");
        }
        model.addAttribute("loggedUser", getCurrentAccount());
        return "admin";
    }


    /**
     * @return переход на страницу для пользователя с ролью MODERATOR
     */
    @GetMapping(value = "/moderator")
    public String printModeratorPage(Model model) {
        if (!ParameterService.printModeratorPageEnabled.getValue()) {
            log.debug("Метод printModeratorPage выключен параметром printModeratorPageEnabled = false");
            throw new RuntimeException("Method printModeratorPage is disabled by parameter printModeratorPageEnabled");
        }
        model.addAttribute("loggedUser", getCurrentAccount());
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
        if (!ParameterService.printRegistrationPageEnabled.getValue()) {
            log.debug("Метод printRegistrationPage выключен параметром printRegistrationPageEnabled = false");
            throw new RuntimeException("Method printRegistrationPage is disabled by parameter printRegistrationPageEnabled");
        }
        model.addAttribute("user", new User());
        return "registration";
    }


    /**
     * Метод для определения защищеной области для входа зарегистрированного пользоватлея
     *
     * @return Переход на ссылку в зависимости от роли, по умолчанию переход на /user
     */
    @GetMapping(value = "/authority")
    public String chooseRole() {
        if (!ParameterService.chooseRoleEnabled.getValue()) {
            log.debug("Метод chooseRole выключен параметром chooseRoleEnabled = false");
            throw new RuntimeException("Method chooseRole is disabled by parameter chooseRoleEnabled");
        }
        String role = getCurrentAccount().getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(","));

        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        if (role.contains("ROLE_MODERATOR")) {
            return "redirect:/moderator";
        } else {
            return "redirect:/user";
        }
    }


    /**
     * Метод для подстановки в поля формы регистрации данных полученных от сервера аутентификции (Google)
     *
     * @param model          - сущность для обмена информацией между методом и html.
     * @param authentication - объект, с данными пользователя прошедшего аутентификацию на сервере аутентификации.
     * @return возвращает страницу с формой регистрации, с предварительно заполеннными полями "Имя", "Фамимлия" и "email"
     */
    @GetMapping(value = "/oauth2reg")
    public String user(Model model, Authentication authentication) {
        if (!ParameterService.oauth2regUserEnabled.getValue()) {
            log.debug("Метод user выключен параметром oauth2regUserEnabled = false");
            throw new RuntimeException("Method user is disabled by parameter oauth2regUserEnabled");
        }

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        User user = new User();
        user.setEmail(principal.getEmail());
        user.setFirstName(principal.getName());
        user.setLastName(principal.getLastName());


        model.addAttribute("user", user);
        return "registration";
    }
}

