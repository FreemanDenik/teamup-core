package ru.team.up.input.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Moderator;

import java.util.List;

/**
 * Интерфейс для поиска, обновления и удаления модераторов
 */
public interface ModeratorServiceRest {

    Account getModeratorById(Long id);

    List<Account> getAllModerators();

    Account saveModerator(Account moderator);

    Moderator updateModerator(Moderator moderator, Long id);

    void deleteModerator(Long id);
}
