package ru.team.up.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.service.ModeratorsSessionsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.team.up.core.entity.Role.ROLE_MODERATOR;
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LogoutHandler implements LogoutSuccessHandler {

    private ModeratorsSessionsService moderatorsSessionsService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        Account account = (Account) authentication.getPrincipal();
        if (account.getRole() == ROLE_MODERATOR) {
            ModeratorSession moderatorSession = moderatorsSessionsService.getModeratorsSessionByModerator(account.getId());
            moderatorsSessionsService.removeModeratorSession(moderatorSession.getId());
        }
    }
}
