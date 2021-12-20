package ru.team.up.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.team.up.auth.service.UserServiceAuth;
import ru.team.up.auth.service.impl.UserDetailsImpl;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

import java.util.Map;

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

    private Account getCurrentAccount() {
        String email;
        if (SecurityContextHolder.getContext().getAuthentication().toString().contains ("given_name")) {
            email = ((DefaultOidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        } else {
            email = ((Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        }
        return (Account) userDetails.loadUserByUsername(email);
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
        model.addAttribute("loggedUser", getCurrentAccount());
        return "user";
    }

    /**
     * @return переход на страницу для пользователя с ролью ADMIN
     */
    @GetMapping(value = "/admin")
    public String printAdminPage(Model model) {
        model.addAttribute("loggedUser", getCurrentAccount());
        return "admin";
    }


    /**
     * @return переход на страницу для пользователя с ролью MODERATOR
     */
    @GetMapping(value = "/moderator")
    public String printModeratorPage(Model model) {
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

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
//    @Autowired
//    private PrincipalExtractor principalExtractor;

    @GetMapping("/oauth2reg")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient client = authorizedClientService
//                .loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
       // principalExtractor.toString();
//        System.out.println("         !!!!!!!        " + client.getPrincipalName() + "      !!!!!!!      ");
//
//        String userInfoEndpointUri = client.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUri();

//        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
//                    .getTokenValue());
//            HttpEntity entity = new HttpEntity("", headers);
//            ResponseEntity<Map> response = restTemplate
//                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//            Map userAttributes = response.getBody();
//            System.out.println("--------------!!!!!!!!!!!!!!!!!!!!!------------------------------------------");
//            System.out.println(userAttributes.toString());
//
//            //model.addAttribute("name", userAttributes.get("name"));
//        }

        return "registration";
    }
}

