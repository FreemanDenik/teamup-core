//package ru.team.up.input.controllerPrivateTest;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.team.up.core.entity.Account;
//import ru.team.up.core.entity.Moderator;
//import ru.team.up.core.service.ModeratorService;
//import ru.team.up.input.controller.privateController.ModeratorController;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class TeamupInputModeratorPrivateControllerTests {
//
//    @Mock
//    private ModeratorService moderatorService;
//
//    @Autowired
//    @InjectMocks
//    private ModeratorController moderatorController;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    Account moderator = Moderator.builder()
//            .firstName("Moderator")
//            .lastName("ModeratorLastName")
//            .middleName("ModeratorMiddleName")
//            .username("ModeratorLogin")
//            .email("moderator@mail.ru")
//            .password("1234")
//            .accountCreatedTime(LocalDate.now())
//            .lastAccountActivity(LocalDateTime.now())
//            .amountOfCheckedEvents(10L)
//            .amountOfDeletedEvents(11L)
//            .amountOfClosedRequests(12L)
//            .build();
//
//    ArrayList<Account> listModerator = new ArrayList<>();
//
//    @Test
//    public void testCreateModerator() {
//        when(moderatorService.saveModerator(moderator)).thenReturn(moderator);
//        Assert.assertEquals(201, moderatorController.createModerator( moderator).getStatusCodeValue());
//    }
//
//    @Test
//    public void testGetOneById() {
//        when(moderatorService.getOneModerator(moderator.getId())).thenReturn(moderator);
//        Assert.assertEquals(200, moderatorController.getOneModerator(moderator.getId()).getStatusCodeValue());
//    }
//
//    @Test
//    public void testGetAllModerator() {
//        listModerator.add(moderator);
//        when(moderatorService.getAllModerators()).thenReturn(listModerator);
//        Assert.assertEquals(200, moderatorController.getAllModerators().getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateModerator() {
////        when(moderatorService.saveModerator(moderator)).thenReturn(moderator);
////        when(moderatorService.moderatorIsExistsById(moderator.getId())).thenReturn(true);
////        Assert.assertEquals(200 , moderatorController.updateModerator(moderator,moderator.getId ()).getStatusCodeValue());
//    }
//
//    @Test
//    public void testDeleteModerator() {
//        Assert.assertEquals(202 , moderatorController.deleteModerator(moderator.getId()).getStatusCodeValue());
//    }
//}
