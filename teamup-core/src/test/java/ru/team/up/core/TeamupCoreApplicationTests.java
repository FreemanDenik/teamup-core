package ru.team.up.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;
import ru.team.up.core.service.EventService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest

class TeamupCoreApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    private Admin adminTest;

    private Moderator moderatorTest;

    private Interests interestsTest, interestsTest1;

    private Set<Interests> interestsSet = new HashSet<>();

    private Set<User> subscriberSetWithTwoUsers = new HashSet<>();

    private Set<User> subscriberSetWithOneUser = new HashSet<>();

    private Set<User> subscriberSetWithoutUsers = new HashSet<>();

    private Set<User> subscriberSetWithTwoUsersForMessage = new HashSet<>();

    private User userTest;

    @BeforeEach
    public void setUpEntity() {
        adminTest = Admin.builder().name("testAdmin").lastName("testAdminLastName").middleName("testAdminMiddleName")
                .login("testAdminLogin").email("test@mail.ru").password("0").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).build();

        moderatorTest = Moderator.builder().name("testModerator").lastName("testModeratorLastName").middleName("testModeratorMiddleName")
                .login("testModeratorLogin").email("moderator@mail.ru").password("1").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).amountOfCheckedEvents(10L).amountOfDeletedEvents(11L).amountOfClosedRequests(12L)
                .build();

        interestsTest = Interests.builder().title("Football")
                .shortDescription("Like to play football").build();

        interestsTest1 = Interests.builder().title("Fishing")
                .shortDescription("Like to going fishing").build();

        interestsSet.add(interestsTest);
        interestsSet.add(interestsTest1);

        userTest = User.builder().name("testUser").lastName("testUserLastName").middleName("testUserMiddleName")
                .login("testUserLogin").email("testUser@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Moskow").age(30).aboutUser("testUser").userInterests(interestsSet).build();

    }

    @Test
    void adminTest(){

        adminRepository.save(adminTest);

        Assert.assertNotNull(adminRepository.findById(1L));

        Admin adminBD = adminRepository.findById(1L).get();

        Assert.assertNotNull(adminBD.getName());
        Assert.assertNotNull(adminBD.getLastName());
        Assert.assertNotNull(adminBD.getLogin());
        Assert.assertNotNull(adminBD.getEmail());
        Assert.assertNotNull(adminBD.getPassword());
        Assert.assertNotNull(adminBD.getAccountCreatedTime());
        Assert.assertNotNull(adminBD.getLastAccountActivity());

        adminRepository.deleteById(1L);
        Assert.assertEquals(adminRepository.findById(1L), Optional.empty());
    }

    @Test
    void moderatorTest(){

        moderatorRepository.save(moderatorTest);

        Assert.assertNotNull(moderatorRepository.findById(1L));

        Moderator moderatorBD = moderatorRepository.findById(1L).get();

        Assert.assertNotNull(moderatorBD.getName());
        Assert.assertNotNull(moderatorBD.getLastName());
        Assert.assertNotNull(moderatorBD.getLogin());
        Assert.assertNotNull(moderatorBD.getEmail());
        Assert.assertNotNull(moderatorBD.getPassword());
        Assert.assertNotNull(moderatorBD.getAccountCreatedTime());
        Assert.assertNotNull(moderatorBD.getLastAccountActivity());

        moderatorRepository.deleteById(1L);
        Assert.assertEquals(moderatorRepository.findById(1L), Optional.empty());
    }

    @Test
    void userTest(){

        interestsRepository.save(interestsTest);
        interestsRepository.save(interestsTest1);

        Assert.assertNotNull(interestsRepository.findById(1L));
        Assert.assertNotNull(interestsRepository.findById(2L));

        Interests interestsBD = interestsRepository.findById(1L).get();

        Assert.assertNotNull(interestsBD.getTitle());
        Assert.assertNotNull(interestsBD.getShortDescription());

        Interests interestsBD1 = interestsRepository.findById(2L).get();

        Assert.assertNotNull(interestsBD1.getTitle());
        Assert.assertNotNull(interestsBD1.getShortDescription());


        userRepository.save(userTest);

        Assert.assertNotNull(userRepository.findById(1L));

        User userBD = userRepository.findById(1L).get();

        Assert.assertNotNull(userBD.getName());
        Assert.assertNotNull(userBD.getLastName());
        Assert.assertNotNull(userBD.getLogin());
        Assert.assertNotNull(userBD.getEmail());
        Assert.assertNotNull(userBD.getPassword());
        Assert.assertNotNull(userBD.getAccountCreatedTime());
        Assert.assertNotNull(userBD.getLastAccountActivity());
        Assert.assertNotNull(userBD.getAge());
        Assert.assertNotNull(userBD.getUserInterests());

        interestsRepository.deleteById(1L);
        Assert.assertEquals(interestsRepository.findById(1L), Optional.empty());

        interestsRepository.deleteById(2L);
        Assert.assertEquals(interestsRepository.findById(2L), Optional.empty());

        userRepository.deleteById(1L);
        Assert.assertEquals(userRepository.findById(1L), Optional.empty());
    }

    @Test
    @Transactional
    void subscriberTest(){

        User subscriber1 = User.builder().name("testSubscriber1").lastName("testSubscriber1LastName")
                .middleName("testSubscriber1sMiddleName")
                .login("testSubscriber1Login").email("testSubscriber1@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Rostov-on-Don").age(35).aboutUser("testSubscriber1").build();

        User subscriber2 = User.builder().name("testSubscriber2").lastName("testSubscriber2LastName")
                .middleName("testSubscriber2sMiddleName")
                .login("testSubscriber2Login").email("testSubscriber2@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Minsk").age(40).aboutUser("testSubscriber2").build();

        subscriberSetWithTwoUsers.add(subscriber1);
        subscriberSetWithTwoUsers.add(subscriber2);

        userRepository.save(subscriber1);
        userRepository.save(subscriber2);

        userTest = User.builder().name("testUserWithSubscribers").lastName("testUserWithSubscribersLastName")
                .middleName("testUserWithSubscribersMiddleName")
                .login("testUserLogin").email("testUser@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Moskow").age(30).aboutUser("testUser").subscribers(subscriberSetWithTwoUsers).build();

        userRepository.save(userTest);

        Assert.assertNotNull(userRepository.findById(4L).get().getSubscribers());


        subscriberSetWithOneUser.add(subscriber2);

        User userBD = userRepository.findById(4L).get();
        userBD.setSubscribers(subscriberSetWithOneUser);
        userRepository.save(userBD);

        userRepository.deleteById(2L);

        Assert.assertNotNull(userRepository.findById(4L).get().getSubscribers());


        userBD.setSubscribers(subscriberSetWithoutUsers);
        userRepository.save(userBD);

        userRepository.deleteById(3L);

        Assert.assertEquals(userRepository.findById(4L).get().getSubscribers(), Collections.emptySet());
    }

    @Test
    void sendMessageTest(){
        User subscriber1 = User.builder().name("testSubscriber1").lastName("testSubscriber1LastName")
                .middleName("testSubscriber1sMiddleName")
                .login("testSubscriber1Login").email("testSubscriber1@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Rostov-on-Don").age(35).aboutUser("testSubscriber1").build();

        User subscriber2 = User.builder().name("testSubscriber2").lastName("testSubscriber2LastName")
                .middleName("testSubscriber2sMiddleName")
                .login("testSubscriber2Login").email("testSubscriber2@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Minsk").age(40).aboutUser("testSubscriber2").build();

        subscriberSetWithTwoUsersForMessage.add(subscriber1);
        subscriberSetWithTwoUsersForMessage.add(subscriber2);

        userRepository.save(subscriber1);
        userRepository.save(subscriber2);

        User userTestMessage = User.builder().name("testUserWithSubscribers").lastName("testUserWithSubscribersLastName")
                .middleName("testUserWithSubscribersMiddleName")
                .login("testUserLogin").email("testUser@mail.ru").password("3").accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now()).city("Moskow").age(30).aboutUser("testUser").subscribers(subscriberSetWithTwoUsersForMessage).build();

        userRepository.save(userTestMessage);

        EventType eventType = EventType.builder().type("Game").build();
        eventTypeRepository.save(eventType);

        Event event = Event.builder().eventName("Football game").descriptionEvent("Join people to play football math")
                .placeEvent("Moskow").timeEvent(LocalDateTime.now()).authorId(userTestMessage).eventType(eventType).build();

        eventService.saveEvent(event);
    }
}
