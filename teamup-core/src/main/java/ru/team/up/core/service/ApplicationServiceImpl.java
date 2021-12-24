package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.ApplicationRepository;
import ru.team.up.core.repositories.UserMessageRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationServiceImpl implements ApplicationService{

    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;
    private UserMessageRepository userMessageRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Application> getAllApplicationsByEventId(Long id) {

        log.debug("Старт метода List<Application> getAllApplicationsByEventId()");
        List<Application> applications = Optional.of(applicationRepository.findAllByEventId(id))
                .orElseThrow(NoContentException::new);

        log.debug("Получили список всех заявок по мероприятию из БД {}", applications);
        return applications;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Application> getAllApplicationsByUserId(Long id) {

        log.debug("Старт метода List<Application> getAllApplicationsByUserId()");
        List<Application> applications = Optional.of(applicationRepository.findAllByUserId(id))
                .orElseThrow(NoContentException::new);

        log.debug("Получили список всех заявок по пользователю из БД {}", applications);
        return applications;
    }


    @Override
    @Transactional(readOnly = true)
    public Application getApplication(Long id) {

        log.debug("Старт метода getApplication(Long id) с параметром {}", id);
        Application application = Optional.of(applicationRepository.getOne(id))
                .orElseThrow(() -> new UserNotFoundException(id));

        log.debug("Получили заявку из БД {}", application);
        return application;
    }


    @Override
    @Transactional
    public Application saveApplication(Application application, User user) {

        log.debug("Получаем из БД пользователя создавшего заявку");
        User userCreatedApplicationDB = userRepository.findById(application.getUser().getId()).get();


        log.debug("Создаем и сохраняем сообщение");
        UserMessage message = UserMessage.builder().messageOwner(userCreatedApplicationDB)
                .message("Пользователь " + userCreatedApplicationDB.getName())
                .status("new")
                .messageCreationTime(LocalDateTime.now()).build();
        userMessageRepository.save(message);


        log.debug("Отправка сообщения пользователю");
                Set<UserMessage> savedMessage = user.getUserMessages();
                savedMessage.add(message);
                user.setUserMessages(savedMessage);
                userRepository.save(user);


        log.debug("Старт метода Application saveApplication(Application application) с параметром {}", application);

        Application save = applicationRepository.save(application);
        log.debug("Сохранили заявку в БД {}", save);

        return save;
    }


    @Override
    @Transactional
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

}
