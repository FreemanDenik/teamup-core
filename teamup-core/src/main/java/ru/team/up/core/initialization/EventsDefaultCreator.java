package ru.team.up.core.initialization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Event;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.EventTypeRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Component
@Transactional
public class EventsDefaultCreator {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public EventsDefaultCreator(EventRepository eventRepository,
                                EventTypeRepository eventTypeRepository,
                                UserRepository userRepository,
                                StatusRepository statusRepository) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }

    @Bean("EventsDefaultCreator")
    @DependsOn({"EventsTypeDefaultCreator", "StatusDefaultCreator", "UsersDefaultCreator"})
    public void eventsDefaultCreator() {

        eventRepository.save(Event.builder()
                .id(1L)
                .eventName("Хакатон")
                .descriptionEvent("Хакатоны и лекции по искусственному интеллекту – первый и самый" +
                        "масштабный проект по ИИ в России")
                .placeEvent("пр. Михаила Нагибина, 3а, Ростов-на-Дону, Ростовская обл., 344018")
                .city("Ростов-на-Дону")
                .timeEvent(LocalDateTime.of(2021, 9, 24, 10,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 80)
                .eventType(eventTypeRepository.getOne(3L))
                .authorId(userRepository.findUserById(7L))
                .status(statusRepository.getOne(3L))
                .build());

        eventRepository.save(Event.builder()
                .id(2L)
                .eventName("Встреча выпускников KATA")
                .descriptionEvent("Приглашаем всех выпускников КАТА Академии")
                .placeEvent("набережная Обводного канала, 74Д, Санкт-Петербург, 190013")
                .city("Санкт-Петербург")
                .timeEvent(LocalDateTime.of(2022, 2, 22, 12,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 110)
                .eventType(eventTypeRepository.getOne(1L))
                .authorId(userRepository.findUserById(5L))
                .status(statusRepository.getOne(1L))
                .build());

        eventRepository.save(Event.builder()
                .id(3L)
                .eventName("Мультимедийный проект «Айвазовский, Кандинский, Дали и Бэнкси. Ожившие полотна»")
                .descriptionEvent("«Люмьер-Холл» представляет мультимедийный проект, посвящённый творчеству четырёх " +
                        "совершенно разных художников")
                .placeEvent("ул. Косыгина, 28, Москва, 119270")
                .city("Москва")
                .timeEvent(LocalDateTime.of(2022, 3, 10, 11,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 110)
                .eventType(eventTypeRepository.getOne(2L))
                .authorId(userRepository.findUserById(2L))
                .status(statusRepository.getOne(2L))
                .build());

        eventRepository.save(Event.builder()
                .id(4L)
                .eventName("Семинар по Йоге")
                .descriptionEvent("Недельный йога марафон")
                .placeEvent("yлица Виноградная, 33, Сочи, Краснодарский край, 354008")
                .city("Сочи")
                .timeEvent(LocalDateTime.of(2022, 6, 7, 9,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 20)
                .eventType(eventTypeRepository.getOne(4L))
                .authorId(userRepository.findUserById(9L))
                .status(statusRepository.getOne(2L))
                .build());
    }
}
