package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Image;
import ru.team.up.core.repositories.AccountRepository;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.ImageRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService{
    private ImageRepository imageRepository;
    private AccountRepository accountRepository;
    private EventRepository eventRepository;

    /**
     * @param image изоброжение которое добавляет пльзователь
     */

    @Override
    public void saveImageForAccount(Image image, String email) {
        log.debug("Сохранение изображения аккаунта {} в БД", image);
        imageRepository.save(image);
        Image newImage = imageRepository.save(image);
        Account newAccount = accountRepository.findByEmail(email);
        newAccount.setImage(newImage);
        accountRepository.save(newAccount);
    }

    /**
     * @param image изоброжение которое добавляет пльзователь
     */

    @Override
    public void updateImageForAccount(Image image, String email) {
        log.debug("Обновление изображения аккаунта {} в БД", image);
        imageRepository.save(image);
        Image newImage = imageRepository.save(image);
        Account newAccount = accountRepository.findByEmail(email);
        newAccount.setImage(newImage);
        accountRepository.save(newAccount);
    }

    /**
     * @param image изоброжение которое добавляет пльзователь
     */

    @Override
    public void saveImageForEvent(Image image, Long eventId) {
        log.debug("Сохранение изображения мероприятия {} в БД", image);
        imageRepository.save(image);
        Image newImage = imageRepository.save(image);
        Event event = eventRepository.getOne(eventId);
        event.setImage(newImage);
        eventRepository.save(event);
    }

    /**
     * @param image изоброжение которое добавляет пльзователь
     */

    @Override
    public void updateImageForEvent(Image image, Long eventId) {
        log.debug("Обновление изображения мероприятия {} в БД", image);
        imageRepository.save(image);
        Image newImage = imageRepository.save(image);
        Event event = eventRepository.getOne(eventId);
        event.setImage(newImage);
        eventRepository.save(event);
    }
}
