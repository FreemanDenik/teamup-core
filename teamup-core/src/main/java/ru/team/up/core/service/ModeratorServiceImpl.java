package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.Role;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.AccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 * <p>
 * Класс сервиса для управления пользователями ru.team.up.core.entity.Moderator
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorServiceImpl implements ModeratorService {
    private AccountRepository accountRepository;
    private PasswordEncoder encoder;

    /**
     * @return Возвращает коллекцию Moderator.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllModerators() {
        log.debug("Старт метода List<Moderator> getAllModerators()");

        List<Account> moderators = Optional.of(accountRepository.findAllByRole(Role.ROLE_MODERATOR))
                .orElseThrow(NoContentException::new);
        log.debug("Получили список всех модераторов из БД {}", moderators);

        return moderators;
    }

    /**
     * @param id Уникальный ключ ID пользователя
     * @return Находит в БД пользователя по ID и возвращает его.
     * Если пользователь с переданным ID не найден в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    @Transactional(readOnly = true)
    public Account getOneModerator(Long id) {
        log.debug("Старт метода Moderator getOneModerator(Long id) с параметром {}", id);

        Account moderator = Optional.of(accountRepository.findById(id)).get()
                .orElseThrow(() -> new UserNotFoundIDException(id));
        log.debug("Получили модератора из БД {}", moderator);
        return moderator;
    }

    /**
     * @param moderator Объект класса ru.team.up.core.entity.Moderator
     * @return Возвращает сохраненный в БД объект moderator
     */
    @Override
    @Transactional
    public Account saveModerator(Account moderator) {
        log.debug("Старт метода Moderator saveModerator(Moderator user) с параметром {}", moderator);

        Moderator newModerator = Moderator.builder()
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_MODERATOR)
                .password(encoder.encode(moderator.getPassword()))
                .firstName(moderator.getFirstName())
                .lastName(moderator.getLastName())
                .middleName(moderator.getMiddleName())
                .username(moderator.getUsername())
                .email(moderator.getEmail())
                .build();

        Account save = accountRepository.save(newModerator);
        log.debug("Создали нового модератора в БД {}", save);
        return save;
    }

    @Override
    public Moderator updateModerator(Moderator moderator) {
        log.debug("Старт метода Moderator updateModerator(Moderator moderator) с параметром {}", moderator);
        Account old = accountRepository.findById(moderator.getId()).get();
        log.debug("accountRepository достает из базы объект Moderator");
        moderator.setAccountCreatedTime(old.getAccountCreatedTime());
        moderator.setLastAccountActivity(LocalDateTime.now());
        moderator.setRole(Role.ROLE_MODERATOR);

        if (moderator.getPassword() == null) {
            moderator.setPassword(old.getPassword());
        } else {
            moderator.setPassword(encoder.encode(moderator.getPassword()));
        }

        accountRepository.save(moderator);
        return moderator;
    }

    /**
     * @param id Объект класса ru.team.up.core.entity.Moderator
     *           Метод удаляет пользователя из БД
     */
    @Override
    @Transactional
    public void deleteModerator(Long id) {
        log.debug("Старт метода void deleteModerator(Long id) с параметром {}", id);
        accountRepository.deleteById(id);
        log.debug("Удалили модератор из БД {}", id);
    }

    @Override
    @Transactional
    public boolean moderatorIsExistsById(Long id) {
        log.debug("Старт метода boolean moderatorIsExistsById(Long id) с параметром {}", id);
        boolean exists = accountRepository.existsById(id);
        if (exists) {
            log.debug("Модератор с Id {} есть в БД", id);
        } else {
            log.debug("Модератора с Id {} не существует", id);
        }
        return exists;
    }
}
