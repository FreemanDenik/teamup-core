package ru.team.up.core.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class TeamupCoreRepositoryTests extends Assertions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AssignedEventsRepository assignedEventsRepository;

    @Autowired
    private ModeratorSessionRepository moderatorSessionRepository;

    private Admin adminTest;

    private Moderator moderatorTest;

    private Interests interestsTest1, interestsTest2;

    private User userTest,subscriberTest1, subscriberTest2;

    private Status statusTest;

    private EventType eventTypeTest;

    private Event eventTest;

    private UserMessage userMessageTest;

    private Application applicationTest;

    private City cityTest;

    private AssignedEvents assignedEventsTest1, assignedEventsTest2;

    private ModeratorSession moderatorSessionTest1, moderatorSessionTest2;

    @BeforeEach
    public void setUpEntity() {
        adminTest = Admin.builder()
                .firstName("testAdmin")
                .lastName("testAdminLastName")
                .middleName("testAdminMiddleName")
                .username("testAdminLogin")
                .email("testAdmin@mail.ru")
                .password("admin")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .build();

        moderatorTest = Moderator.builder()
                .firstName("testModerator")
                .lastName("testModeratorLastName")
                .middleName("testModeratorMiddleName")
                .username("testModeratorLogin")
                .email("testModerator@mail.ru")
                .password("moderator")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .amountOfCheckedEvents(10L)
                .amountOfDeletedEvents(11L)
                .amountOfClosedRequests(12L)
                .build();

        interestsTest1 = Interests.builder()
                .title("Football")
                .shortDescription("Like to play football")
                .build();

        interestsTest2 = Interests.builder()
                .title("Fishing")
                .shortDescription("Like to going fishing")
                .build();

        userTest = User.builder()
                .firstName("testUser")
                .lastName("testUserLastName")
                .middleName("testUserMiddleName")
                .username("testUserLogin")
                .email("testUser@mail.ru")
                .password("user")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .birthday(LocalDate.of (1992, 1, 20))
                .aboutUser("testUserAbout")
                .build();

        subscriberTest1 = User.builder()
                .firstName("testSubscriber1Name")
                .lastName("testSubscriber1LastName")
                .middleName("testSubscriber1MiddleName")
                .username("testSubscriber1Login")
                .email("testSubscriber1@mail.ru")
                .password("subscriber1")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Rostov-on-Don")
                .birthday(LocalDate.of (1987, 1, 20))
                .aboutUser("testSubscriber1About")
                .build();

        subscriberTest2 = User.builder()
                .firstName("testSubscriber2Name")
                .lastName("testSubscriber2LastName")
                .middleName("testSubscriber2MiddleName")
                .username("testSubscriber2Login")
                .email("testSubscriber2@mail.ru")
                .password("testSubscriber2")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Minsk")
                .birthday(LocalDate.of (1982, 1, 20))
                .aboutUser("testSubscriber2About")
                .build();

        statusTest = Status.builder()
                .status("New")
                .build();

        eventTypeTest = EventType.builder()
                .type("Game")
                .build();

        eventTest = Event.builder()
                .eventName("Football game")
                .descriptionEvent("Join people to play football math")
                .city("Moscow")
                .placeEvent("Stadium")
                .eventNumberOfParticipant((byte)20)
                .timeEvent(LocalDateTime.now())
                .timeEndEvent(LocalDateTime.now().plusDays(1L))
                .status(statusTest)
                .eventType(eventTypeTest)
                .authorId(userTest)
                .build();

        userMessageTest = UserMessage.builder()
                .message("Благодарю за подписку!")
                .status(statusTest)
                .messageCreationTime(LocalDateTime.now())
                .build();

        applicationTest = Application.builder()
                .event(eventTest)
                .user(userTest)
                .build();

        cityTest = City.builder()
                .name("Краснодар")
                .subject("Краснодарский край")
                .lat("45°02′")
                .lon("38°59′")
                .build();

        // 7L и 8L - случайно выбранные числа, чтобы избежать цепочки зависимостей
        assignedEventsTest1 = AssignedEvents.builder()
                .eventId(7L)
                .moderatorId(7L)
                .build();

        assignedEventsTest2 = AssignedEvents.builder()
                .eventId(8L)
                .moderatorId(8L)
                .build();

        moderatorSessionTest1 = ModeratorSession.builder()
                .moderatorId(11L)
                .amountOfModeratorsEvents(21L)
                .build();

        moderatorSessionTest2 = ModeratorSession.builder()
                .moderatorId(12L)
                .amountOfModeratorsEvents(22L)
                .build();
    }

    @Test
    @Transactional
    void adminTest() {
        // Сохранили тестового админа в БД
        adminRepository.save(adminTest);
        // Получили назначенный ID
        Long testAdminId = adminTest.getId();

        // Проверка на наличие тестового админа с полученным ID
        assertNotNull(adminRepository.findById(testAdminId));

        // Создали нового тестового админа и получили его из БД по ID
        Admin testAdminBD = (Admin) adminRepository.findById(testAdminId).get();

        // Проверили данные на совпадение
        assertEquals(testAdminBD.getFirstName(), adminTest.getFirstName());
        assertEquals(testAdminBD.getLastName(), adminTest.getLastName());
        assertEquals(testAdminBD.getUsername(), adminTest.getUsername());
        assertEquals(testAdminBD.getEmail(), adminTest.getEmail());
        assertEquals(testAdminBD.getPassword(), adminTest.getPassword());
        assertEquals(testAdminBD.getAccountCreatedTime(), adminTest.getAccountCreatedTime());
        assertEquals(testAdminBD.getLastAccountActivity(), adminTest.getLastAccountActivity());

        // Удалили тестового админа по ID
        adminRepository.deleteById(testAdminId);
        // Проверили что тестового админа больше нет в БД
        assertEquals(adminRepository.findById(testAdminId), Optional.empty());
    }

    @Test
    void adminTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()-> adminRepository.save(Admin.builder().build()));
    }

    @Test
    @Transactional
    void moderatorTest() {
        // Сохранили тестового модератора в БД
        moderatorRepository.save(moderatorTest);
        // Получили назначенный ID
        Long testModeratorId = moderatorTest.getId();

        // Проверка на наличие тестового модератора с полученным ID
        assertNotNull(moderatorRepository.findById(testModeratorId));

        // Создали нового тестового модератора и получили его из БД по ID
        Moderator moderatorBD = (Moderator) moderatorRepository.findById(testModeratorId).get();

        // Проверили данные на совпадение
        assertEquals(moderatorBD.getFirstName(), moderatorTest.getFirstName());
        assertEquals(moderatorBD.getLastName(), moderatorTest.getLastName());
        assertEquals(moderatorBD.getUsername(), moderatorTest.getUsername());
        assertEquals(moderatorBD.getEmail(), moderatorTest.getEmail());
        assertEquals(moderatorBD.getPassword(), moderatorTest.getPassword());
        assertEquals(moderatorBD.getAmountOfCheckedEvents(), moderatorTest.getAmountOfCheckedEvents());
        assertEquals(moderatorBD.getAmountOfDeletedEvents(), moderatorTest.getAmountOfDeletedEvents());
        assertEquals(moderatorBD.getAmountOfClosedRequests(), moderatorTest.getAmountOfClosedRequests());
        assertEquals(moderatorBD.getAccountCreatedTime(), moderatorTest.getAccountCreatedTime());
        assertEquals(moderatorBD.getLastAccountActivity(), moderatorTest.getLastAccountActivity());

        // Удалили тестового модератора по ID
        moderatorRepository.deleteById(testModeratorId);
        // Проверили что тестового модератора больше нет в БД
        assertEquals(moderatorRepository.findById(testModeratorId), Optional.empty());
    }

    @Test
    void moderatorTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()-> moderatorRepository.save(Moderator.builder().build()));
    }

    @Test
    @Transactional
    void userTest() {
        // Сохранили интересы
        interestsRepository.save(interestsTest1);
        interestsRepository.save(interestsTest2);
        // Получили назначенные ID
        Long interestIdTest1 = interestsTest1.getId();
        Long interestIdTest2 = interestsTest2.getId();
        // Создали список интересов
        Set<Interests> interestsSet = new HashSet<>();
        interestsSet.add(interestsTest1);
        interestsSet.add(interestsTest2);

        // Проверили что интересы с полученным ID существуют
        assertNotNull(interestsRepository.findById(interestIdTest1));
        assertNotNull(interestsRepository.findById(interestIdTest2));

        // Создали новые интересы и получили существующие интересы из БД по назначенным ID
        Interests interestsBD1 = interestsRepository.findById(interestIdTest1).get();
        Interests interestsBD2 = interestsRepository.findById(interestIdTest2).get();

        // Проверили данные на совпадение
        assertEquals(interestsBD1.getTitle(), interestsTest1.getTitle());
        assertEquals(interestsBD1.getShortDescription(), interestsTest1.getShortDescription());
        assertEquals(interestsBD2.getTitle(), interestsTest2.getTitle());
        assertEquals(interestsBD2.getShortDescription(), interestsTest2.getShortDescription());

        // Сохранили тестового пользователя
        User testUser = userTest;
        // Добавили список интересов пользователю
        testUser.setUserInterests(interestsSet);
        userRepository.save(testUser);
        // Получили назначенный ID
        Long testUserId = testUser.getId();
        // Проверка на наличие тестового пользователя на назначенному ID
        assertNotNull(userRepository.findById(testUserId));
        // Создали нового тестового пользователя и получили данные из БД по назначенному ID
        User userBD = (User) userRepository.findById(testUserId).get();

        // Проверили данные на соответствие
        assertEquals(userBD.getFirstName(), userTest.getFirstName());
        assertEquals(userBD.getLastName(), userTest.getLastName());
        assertEquals(userBD.getMiddleName(), userTest.getMiddleName());
        assertEquals(userBD.getUsername(), userTest.getUsername());
        assertEquals(userBD.getEmail(), userTest.getEmail());
        assertEquals(userBD.getPassword(), userTest.getPassword());
        assertEquals(userBD.getAccountCreatedTime(), userTest.getAccountCreatedTime());
        assertEquals(userBD.getLastAccountActivity(), userTest.getLastAccountActivity());
        assertEquals(userBD.getBirthday(), userTest.getBirthday());
        assertEquals(userBD.getCity(), userTest.getCity());
        assertNotNull(userBD.getUserInterests());

        // Удалили первый интерес
        interestsRepository.deleteById(interestIdTest1);
        // Проверили что первый интерес отсутствует в БД
        assertEquals(interestsRepository.findById(interestIdTest1), Optional.empty());
        // Удалили второй интерес
        interestsRepository.deleteById(interestIdTest2);
        // Проверили что второй интерес отсутствует в БД
        assertEquals(interestsRepository.findById(interestIdTest2), Optional.empty());
        // Удалили тестового пользователя
        userRepository.deleteById(testUserId);
        // Проверили что тестовый пользователь отсутствует в БД
        assertEquals(userRepository.findById(testUserId), Optional.empty());
    }

    @Test
    void userTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()-> userRepository.save(User.builder().build()));
    }

    @Test
    @Transactional
    void subscribersTest(){
        // Создали подписчиков
        User subscriber1 = subscriberTest1;
        User subscriber2 = subscriberTest2;

        // Сохранили подписчиков как пользователей
        userRepository.save(subscriber1);
        userRepository.save(subscriber2);

        // Получили назначенные ID
        Long subscriberId1 = subscriber1.getId();
        Long subscriberId2 = subscriber2.getId();

        // Создали множество из двух подписчиков
        Set<User> setTwoSubscribers = new HashSet<>();
        setTwoSubscribers.add(subscriber1);
        setTwoSubscribers.add(subscriber2);

        // Создали пользователя с двумя подписчиками
        User testUser = userTest;
        testUser.setSubscribers(setTwoSubscribers);

        // Сохранили пользователя с двумя подписчиками
        userRepository.save(testUser);
        // Получили назначенные ID
        Long testUserId = testUser.getId();

        // Проверка на наличие подписчиков у пользователя
        assertNotNull(userRepository.findUserById(testUserId).getSubscribers());

        // Создали множество из одного подписчика
        Set<User> setOneSubscriber = new HashSet<>();
        setOneSubscriber.add(subscriber2);

        // Создали нового тестового пользователя и получили его из БД
        User testUserBD = (User) userRepository.findById(testUserId).get();

        // Назначили для нового пользователя одного подписчика
        testUserBD.setSubscribers(setOneSubscriber);
        userRepository.save(testUserBD);

        // Удалили из БД первого подписчика
        userRepository.deleteById(subscriberId1);

        // Проверили что у тестового пользователя остался один подписчик
        assertNotNull(userRepository.findUserById(testUserId).getSubscribers());

        // У тестового пользователя удалили всех подписчиков
        Set<User> subscribersEmpty = new HashSet<>();
        testUserBD.setSubscribers(subscribersEmpty);
        userRepository.save(testUserBD);

        // Удалили из БД второго подписчика
        userRepository.deleteById(subscriberId2);

        // Проверили что у пользователя больше нет подписчиков
        assertEquals(userRepository.findUserById(testUserId).getSubscribers(), Collections.emptySet());
    }

    @Test
    @Transactional
    void eventTest(){
        // Сохранили тестового пользователя
        User testUser = userTest;
        userRepository.save(testUser);
        // Получили назначенный ID
        Long testUserId = testUser.getId();

        // Сохранили Тип мероприятия
        EventType testEventType = eventTypeTest;
        eventTypeRepository.save(testEventType);
        // Получили назначенный ID
        Long testEventTypeId = testEventType.getId();

        // Сохранили Статус мероприятия
        Status testStatus = statusTest;
        statusRepository.save(testStatus);
        // Получили назначенный ID
        Long testStatusId = testStatus.getId();

        // Создали новое мероприятие
        Event testEvent = eventTest;
        // Добавили участников
        testEvent.setParticipantsEvent(Set.of(testUser));
        // Добавили тип мероприятия
        testEvent.setEventType(testEventType);
        // Добавили автора мероприятия
        testEvent.setAuthorId(testUser);
        // Добавили статус мероприятия
        testEvent.setStatus(testStatus);

        // Сохранили новое мероприятие
        eventRepository.save(testEvent);
        // Получили ID нового мероприятия
        Long testEventId = testEvent.getId();
        // Создали новое мероприятие и получили данные из тестового мероприятия из БД
        Event eventBD = eventRepository.getOne(testEventId);
        // Проверили данные на соответствие
        assertEquals(eventBD.getEventName(), eventTest.getEventName());
        assertEquals(eventBD.getDescriptionEvent(), eventTest.getDescriptionEvent());
        assertEquals(eventBD.getPlaceEvent(), eventTest.getPlaceEvent());
        assertEquals(eventBD.getTimeEvent(), eventTest.getTimeEvent());
        assertEquals(eventBD.getParticipantsEvent(), eventTest.getParticipantsEvent());
        assertEquals(eventBD.getEventType(), eventTypeTest);
        assertEquals(eventBD.getAuthorId(), userTest);
        assertEquals(eventBD.getStatus(), statusTest);
        // Удалили мероприятие из БД
        eventRepository.deleteById(testEventId);
        // Проверили что мероприятие отсутствует в БД
        assertEquals(eventRepository.findById(testEventId), Optional.empty());

        // Удалили Статус
        statusRepository.deleteById(testStatusId);
        // Проверили что статус удалён из БД
        assertEquals(statusRepository.findById(testStatusId), Optional.empty());

        // Удалили Тип мероприятия
        eventTypeRepository.deleteById(testEventTypeId);
        // Проверили что Тип мероприятия удалён из БД
        assertEquals(eventTypeRepository.findById(testEventTypeId), Optional.empty());

        // Удалили тестового пользователя
        userRepository.deleteById(testUserId);
        // Проверили что тестовый пользователь удалён из БД
        assertEquals(userRepository.findById(testUserId), Optional.empty());
    }

    @Test
    void eventTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()-> eventRepository.save(Event.builder().build()));
    }

    @Test
    @Transactional
    void userMessageTest() {
        // Создали подписчиков
        User subscriber1 = subscriberTest1;
        User subscriber2 = subscriberTest2;

        // Сохранили подписчиков как пользователей
        userRepository.save(subscriber1);
        userRepository.save(subscriber2);

        // Получили назначенные ID
        Long subscriberId1 = subscriber1.getId();
        Long subscriberId2 = subscriber2.getId();

        // Создали множество из двух подписчиков
        Set<User> setTwoSubscribers = new HashSet<>();
        setTwoSubscribers.add(subscriber1);
        setTwoSubscribers.add(subscriber2);

        // Создали пользователя с двумя подписчиками
        User testUser = userTest;
        testUser.setSubscribers(setTwoSubscribers);

        // Сохранили пользователя с двумя подписчиками
        userRepository.save(testUser);
        // Получили назначенные ID
        Long testUserId = testUser.getId();

        // Проверка на наличие подписчиков у пользователя
        assertNotNull(userRepository.findUserById(testUserId).getSubscribers());

        // Создали тестовое сообщение
        UserMessage testUserMessage = userMessageTest;
        testUserMessage.setMessageOwner(testUser);
        testUserMessage.setUsers(setTwoSubscribers);
        testUserMessage.setMessageReadTime(LocalDateTime.now());
        userMessageRepository.save(testUserMessage);
        // Получили назначенные ID
        Long testUserMessageId = testUserMessage.getId();

        // Создали новое сообщение и получили данные из тестового сообщения из БД
        UserMessage userMessageBD = userMessageRepository.getOne(testUserMessageId);

        // Проверили данные на соответствие
        assertEquals(userMessageBD.getMessageOwner(), testUser);
        assertEquals(userMessageBD.getMessage(), userMessageTest.getMessage());
        assertEquals(userMessageBD.getStatus(), userMessageTest.getStatus());
        assertEquals(userMessageBD.getMessageCreationTime(), userMessageTest.getMessageCreationTime());
        assertNotNull(userMessageBD.getMessageReadTime());
        assertEquals(userMessageBD.getUsers(), setTwoSubscribers);

        // Удалили тестовое сообщение из БД
        userMessageRepository.deleteById(testUserMessageId);
        // Проверили что тестовое сообщение удалено из БД
        assertEquals(userMessageRepository.findById(testUserMessageId), Optional.empty());

        // Удалили тестового пользователя
        userRepository.deleteById(testUserId);
        // Проверили что тестовый пользователь удалён из БД
        assertEquals(userRepository.findById(testUserId), Optional.empty());

        // Удалили из БД первого подписчика
        userRepository.deleteById(subscriberId1);
        // Проверили что первый подписчик удалён
        assertEquals(userRepository.findById(subscriberId1), Optional.empty());

        // Удалили из БД второго подписчика
        userRepository.deleteById(subscriberId2);
        // Проверили что второй подписчик удалён
        assertEquals(userRepository.findById(subscriberId2), Optional.empty());
    }

    // TODO не проходит, скорее всего надо исправить
    @Test
    void userMessageTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()->{userMessageRepository.save(UserMessage.builder().build());
                });
    }

    @Test
    @Transactional
    void applicationTest() {
        // Записываем данные (в этом порядке, чтобы не попасть на DataIntegrityViolationException)
        statusRepository.save(statusTest);
        eventTypeRepository.save(eventTypeTest);
        userRepository.save(userTest);
        eventRepository.save(eventTest);
        applicationRepository.save(applicationTest);

        // Проверка работы методов репозитория
        assertNotNull(applicationRepository.findAllByEventId(1L));
        assertNotNull(applicationRepository.findAllByUserId(1L));
        // Проверка соответствия данных
        assertEquals(userTest, applicationRepository.getOne(1L).getEvent().getAuthorId());

        // Очистка репозиториев в обратном порядке
        applicationRepository.deleteById(applicationTest.getId());
        eventRepository.deleteById(eventTest.getId());
        userRepository.deleteById(userTest.getId());
        eventTypeRepository.deleteById(eventTypeTest.getId());
    }

    // TODO не проходит, скорее всего надо исправить
    @Test
    void applicationTestNull(){
        assertThrows(DataIntegrityViolationException.class,
                ()->{applicationRepository.save(Application.builder().build());
                });
    }

    @Test
    void cityTest() {
        // запись данных в репозиторий
        cityRepository.save(cityTest);

        // проверка наличия нашего города в репозитории
        assertNotNull(cityRepository.findAll());
        assertEquals(1, cityRepository.getSomeCitiesByName("Краснодар").size());
        // проверка методов
        assertEquals("Краснодарский край", cityRepository.getSomeCitiesByName("Краснодар").get(0).getSubject());
        assertEquals("Краснодарский край", cityRepository.findCityByName("Краснодар").getSubject());
        assertEquals("Краснодарский край",
                cityRepository.getCityByNameAndSubject("Краснодар", "Краснодарский край").getSubject());
        assertEquals("Краснодар", cityRepository.getCityByLatAndLon("45°02′", "38°59′").getName());

        // очистка репозиториев
        cityRepository.deleteAll();
    }

    @Test
    void cityTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()->{cityRepository.save(City.builder().build());
                });
    }

    @Test
    void interestsTest() {
        // заполнение репозитория
        interestsRepository.save(interestsTest1);

        // проверка метода и корректности данных
        assertNotNull(interestsRepository.getInterestsById(1L));
        assertEquals("Football", interestsRepository.getInterestsById(1L).getTitle());

        // очистка репозитория
        interestsRepository.deleteAll();
    }

    @Test
    void interestsTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()->{interestsRepository.save(Interests.builder().build());
                });
    }

    // TODO странный репозиторий, надо уточнить принцип работы + там SQLGrammarException
    @Test
    @Transactional
    void assignedEventsTest() {
        // Заполнение репозитория
        statusRepository.save(statusTest);
        eventTypeRepository.save(eventTypeTest);
        userRepository.save(userTest);
        eventRepository.save(eventTest);
        assignedEventsRepository.save(assignedEventsTest1);
        assignedEventsRepository.save(assignedEventsTest2);

        // Проверка заполнения
        assertNotNull(assignedEventsRepository.findAll());
        assertEquals(2, assignedEventsRepository.findAll().size());

        // Проверка методов и корректности данных
        // TODO скорее всего надо добавить List в название метода (и там в запросе надо поменять status на status.id) ??
        assertEquals(1L, assignedEventsRepository.getIdAssignedEventsByModeratorId(7L).get(0));
        assertEquals(2L, assignedEventsRepository.getIdAssignedEventsByModeratorId(8L).get(0));
        //assertEquals(7L, assignedEventsRepository.getIdNotAssignedEvents());

        // проверка наличия мероприятия с Id=1
        assertEquals(1L, eventRepository.getOne(1L).getId());
        // проверка статуса мероприятия с Id=1, что он равен 1
        assertEquals(1L, eventRepository.getOne(1L).getStatus().getId());
        // попытка обновить статус мероприятия с 1 на 33
        assignedEventsRepository.updateEventIdBeforeDeleting(33L, 1L);
        // проверка обновления статуса
        assertEquals(33L, eventRepository.getOne(1L).getStatus().getId());

        // Очистка репозитория
        assignedEventsRepository.deleteAll();
    }

    @Test
    void assignedEventsTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()->{assignedEventsRepository.save(AssignedEvents.builder().build());
                });
    }

    @Test
    void moderatorSessionTest() {
        // Заполняем репозиторий. В сессиях находятся два модератора:
        // в moderatorSessionTest1 : 1-й модератор с id=11, у него висит 21 задача
        // в moderatorSessionTest2 : 2-й модератор с id=12, у него висят 22 задачи
        moderatorSessionRepository.save(moderatorSessionTest1);
        moderatorSessionRepository.save(moderatorSessionTest2);

        // проверка, что обе сессии находятся в репозитории
        assertNotNull(moderatorSessionRepository.findModeratorSessionByModeratorId(11L));
        assertNotNull(moderatorSessionRepository.findModeratorSessionByModeratorId(12L));

        // проверка методов и корректности данных
        assertEquals(22,
                moderatorSessionRepository.findModeratorSessionByModeratorId(12L).getAmountOfModeratorsEvents());
        // у модератора с id=11 меньше задач, поэтому ожидаем, что вернется его id
        assertEquals(11, moderatorSessionRepository.getFreeModerator());
        // TODO изменить условно-бесполезный тест, надо придумать проверку по времени (или изменить метод)
        // Там вообще логика не совсем с названием связана (а может я не до конца понял sql)
        assertTrue(moderatorSessionRepository.getInactiveModerators(LocalDateTime.now()).isEmpty());

        // очистка репозитория
        moderatorSessionRepository.deleteAll();
        assertNull(moderatorSessionRepository.getFreeModerator());
    }

    // TODO не проходит, скорее всего надо исправить
    @Test
    void moderatorSessionTestNull() {
        assertThrows(DataIntegrityViolationException.class,
                ()->{moderatorSessionRepository.save(ModeratorSession.builder().build());
                });
    }
}
