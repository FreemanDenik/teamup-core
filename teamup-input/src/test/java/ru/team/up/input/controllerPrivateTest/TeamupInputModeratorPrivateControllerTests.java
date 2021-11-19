package ru.team.up.input.controllerPrivateTest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.service.ModeratorService;
import ru.team.up.input.controller.privateController.ModeratorController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputModeratorPrivateControllerTests {

    @Mock
    private ModeratorService moderatorService;

    @Autowired
    @InjectMocks
    private ModeratorController moderatorController;

    Moderator moderator = Moderator.builder()
            .id(1L)
            .name("Moderator")
            .lastName("ModeratorLastName")
            .middleName("ModeratorMiddleName")
            .login("ModeratorLogin")
            .email("moderator@mail.ru")
            .password("1234")
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .amountOfCheckedEvents(10L)
            .amountOfDeletedEvents(11L)
            .amountOfClosedRequests(12L)
            .build();

    ArrayList<Moderator> listModerator = new ArrayList<>();

    @Test
    public void testCreateModerator() {
        when(moderatorService.saveModerator(moderator)).thenReturn(moderator);
        Assert.assertEquals(201, moderatorController.createModerator("moderator", moderator).getStatusCodeValue());
    }

    @Test
    public void testGetOneById() {
        when(moderatorService.getOneModerator(moderator.getId())).thenReturn(moderator);
        Assert.assertEquals(200, moderatorController.getOneModerator(moderator.getId()).getStatusCodeValue());
    }

    @Test
    public void testGetAllModerator() throws Exception {
        listModerator.add(moderator);
        when(moderatorService.getAllModerators()).thenReturn(listModerator);
        Assert.assertEquals(200, moderatorController.getAllModerators().getStatusCodeValue());
    }

    @Test
    public void testUpdateModerator() {
        when(moderatorService.saveModerator(moderator)).thenReturn(moderator);
        Assert.assertEquals(200, moderatorController.updateModerator(moderator).getStatusCodeValue());
    }

    @Test
    public void testDeleteModerator(){
        Assert.assertEquals(202, moderatorController.deleteModerator(moderator.getId()).getStatusCodeValue());
    }
}
