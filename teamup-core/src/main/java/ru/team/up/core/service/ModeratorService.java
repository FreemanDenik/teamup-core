package ru.team.up.core.service;

import ru.team.up.core.entity.Moderator;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface ModeratorService {
    List<Moderator> getAllModerators();

    Moderator getOneModerator(Long id);

    Moderator saveModerator(Moderator user);

    void deleteModerator(Moderator user);
}
