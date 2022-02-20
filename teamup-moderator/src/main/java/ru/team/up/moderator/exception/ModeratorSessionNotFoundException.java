package ru.team.up.moderator.exception;

public class ModeratorSessionNotFoundException extends RuntimeException {
    public ModeratorSessionNotFoundException(Long id) {
        super("Не удалось найти сессию модератора по moderatorId: " + id);
    }
}
