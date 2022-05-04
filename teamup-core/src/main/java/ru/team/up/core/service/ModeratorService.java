package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Moderator;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface ModeratorService {
    List<Account> getAllModerators();

    Account getOneModerator(Long id);

    Account saveModerator(Account user);

    Moderator updateModerator(Moderator moderator);

    void deleteModerator(Long id);
    boolean moderatorIsExistsById(Long id);

}
