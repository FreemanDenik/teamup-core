package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.service.ModeratorService;
import ru.team.up.input.service.ModeratorServiceRest;

import java.util.List;

/**
 * Service для работы с модераторами
 */

@Service
@AllArgsConstructor
public class ModeratorServiceRestImpl implements ModeratorServiceRest {

    private ModeratorService moderatorService;


    @Override
    public Account getModeratorById(Long id) {
        return moderatorService.getOneModerator(id);
    }

    @Override
    public List<Account> getAllModerators() {
        return moderatorService.getAllModerators();
    }


    @Override
    public Account saveModerator(Account moderator) {
        return moderatorService.saveModerator(moderator);
    }

    @Override
    public Moderator updateModerator(Moderator moderator, Long id) {
        return moderatorService.updateModerator(moderator);
    }

    @Override
    public void deleteModerator(Long id) {
        moderatorService.deleteModerator(id);

    }
}
