package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
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
@Profile("cdb")
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
                .eventName("Хакатон")
                .descriptionEvent("Хакатоны и лекции по искусственному интеллекту – первый и самый" +
                        "масштабный проект по ИИ в России")
                .placeEvent("пр. Михаила Нагибина, 3а, Ростов-на-Дону, Ростовская обл., 344018")
                .city("Ростов-на-Дону")
                .timeEvent(LocalDateTime.of(2021, 9, 24, 10,0))
                .timeEndEvent(LocalDateTime.of(2021, 9, 30, 18,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 80)
                .countViewEvent(20)
                .eventType(eventTypeRepository.getOne(3L))
                .authorId(userRepository.findUserById(7L))
                .status(statusRepository.getOne(2L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Встреча выпускников KATA")
                .descriptionEvent("Приглашаем всех выпускников КАТА Академии")
                .placeEvent("набережная Обводного канала, 74Д, Санкт-Петербург, 190013")
                .city("Санкт-Петербург")
                .timeEvent(LocalDateTime.of(2022, 2, 22, 12,0))
                .timeEndEvent(LocalDateTime.of(2022, 2, 22, 22,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 110)
                .countViewEvent(15)
                .eventType(eventTypeRepository.getOne(1L))
                .authorId(userRepository.findUserById(5L))
                .status(statusRepository.getOne(1L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Мультимедийный проект «Айвазовский, Кандинский, Дали и Бэнкси. Ожившие полотна»")
                .descriptionEvent("«Люмьер-Холл» представляет мультимедийный проект, посвящённый творчеству четырёх " +
                        "совершенно разных художников")
                .placeEvent("ул. Косыгина, 28, Москва, 119270")
                .city("Москва")
                .timeEvent(LocalDateTime.of(2022, 3, 10, 11,0))
                .timeEndEvent(LocalDateTime.of(2022, 4, 9, 20,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 110)
                .countViewEvent(14)
                .eventType(eventTypeRepository.getOne(2L))
                .authorId(userRepository.findUserById(2L))
                .status(statusRepository.getOne(2L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Семинар по Йоге")
                .descriptionEvent("Недельный йога марафон")
                .placeEvent("yлица Виноградная, 33, Сочи, Краснодарский край, 354008")
                .city("Сочи")
                .timeEvent(LocalDateTime.of(2022, 6, 7, 9,0))
                .timeEndEvent(LocalDateTime.of(2022, 6, 13, 18,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 20)
                .countViewEvent(36)
                .eventType(eventTypeRepository.getOne(4L))
                .authorId(userRepository.findUserById(9L))
                .status(statusRepository.getOne(2L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Выставка «Археология: из прошлого в настоящее»")
                .descriptionEvent("2022 году отмечается 300-летие знаменательного события — проведение первых археологических раскопок в масштабах страны")
                .placeEvent("yлица Чертыгашева, 65, Абакан, Республика Хакасия, 655017")
                .city("Абакан")
                .timeEvent(LocalDateTime.of(2022, 5, 25, 10,0))
                .timeEndEvent(LocalDateTime.of(2022, 6, 24, 18,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 100)
                .countViewEvent(45)
                .eventType(eventTypeRepository.getOne(2L))
                .authorId(userRepository.findUserById(10L))
                .status(statusRepository.getOne(5L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Чемпионат по танцам UDC 2022")
                .descriptionEvent("Раскачаем этот год с Ежегодного чемпионата от #DANCEKБ - Urban Dance Championship 2022")
                .placeEvent("yлица Радищева, 41, Екатеринбург, Свердловская область, 620014")
                .city("Екатеринбург")
                .timeEvent(LocalDateTime.of(2022, 6, 22, 12,0))
                .timeEndEvent(LocalDateTime.of(2022, 6, 25, 17,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 30)
                .countViewEvent(150)
                .eventType(eventTypeRepository.getOne(5L))
                .authorId(userRepository.findUserById(11L))
                .status(statusRepository.getOne(6L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Лыжный марафон 2022 - Праздник Севера")
                .descriptionEvent("Марафон ПраздникСевера проходит с 1973 в рамках Полярной Олимпиады и долгое время оставался единственным марафоном в СССР")
                .placeEvent("улица Долина Уюта, 2, Мурманск, Мурманская область, 183052")
                .city("Мурманск")
                .timeEvent(LocalDateTime.of(2022, 12, 5, 10,0))
                .timeEndEvent(LocalDateTime.of(2022, 12, 5, 18,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 127)
                .countViewEvent(250)
                .eventType(eventTypeRepository.getOne(4L))
                .authorId(userRepository.findUserById(12L))
                .status(statusRepository.getOne(5L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Глубина резкости")
                .descriptionEvent("VI Дальневосточный фотоконкурс")
                .placeEvent("набережная Корабельная, 1, Владивосток, Приморский край, 690091")
                .city("Владивосток")
                .timeEvent(LocalDateTime.of(2022, 6, 8, 17,0))
                .timeEndEvent(LocalDateTime.of(2022, 6, 15, 17,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 50)
                .countViewEvent(120)
                .eventType(eventTypeRepository.getOne(2L))
                .authorId(userRepository.findUserById(13L))
                .status(statusRepository.getOne(1L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Восхождение на г. Казбек 5033м с север")
                .descriptionEvent("Хочешь проверить в себя? Побывать на пятитысячнике или может добавить в копилочку? Вперёд с нами!")
                .placeEvent("площадь Привокзальная, 5, Краснодар, Краснодарский край, 350033")
                .city("Краснодар")
                .timeEvent(LocalDateTime.of(2022, 7, 16, 02,0))
                .timeEndEvent(LocalDateTime.of(2022, 7, 28, 12,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 5)
                .countViewEvent(450)
                .eventType(eventTypeRepository.getOne(4L))
                .authorId(userRepository.findUserById(14L))
                .status(statusRepository.getOne(6L))
                .build());

        eventRepository.save(Event.builder()
                .eventName("Экскурсии по Нарвской заставе и не только...")
                .descriptionEvent("Вас ждет увлекательная экскурсия по уникальным районам города")
                .placeEvent("проспект Стачек, 45, Санкт-Петербург, Ленинградская область, 198097")
                .city("Санкт-Петербург")
                .timeEvent(LocalDateTime.of(2022, 5, 22, 14,0))
                .timeEndEvent(LocalDateTime.of(2022, 7, 22, 19,0))
                .eventUpdateDate(LocalDate.now())
                .eventNumberOfParticipant((byte) 10)
                .countViewEvent(365)
                .eventType(eventTypeRepository.getOne(6L))
                .authorId(userRepository.findUserById(15L))
                .status(statusRepository.getOne(1L))
                .build());
    }
}
