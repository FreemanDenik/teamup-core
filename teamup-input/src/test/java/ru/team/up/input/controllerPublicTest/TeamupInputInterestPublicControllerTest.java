package ru.team.up.input.controllerPublicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.entity.User;
import ru.team.up.input.controller.publicController.InterestRestControllerPublic;
import ru.team.up.input.service.InterestServiceRest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputInterestPublicControllerTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Mock
    private InterestServiceRest interestServiceRest;

    @InjectMocks
    InterestRestControllerPublic interestRestControllerPublic;

    Interests art = Interests.builder()
            .title("Искусство")
            .shortDescription("Paintings, paintings, graphics")
            .build();

    User testUser = User.builder()
            .firstName("Dmitry")
            .lastName("Paltusov")
            .middleName("Victorovich")
            .username("mityi")
            .email("mityi@bk.ru")
            .password("666")
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .city("Arkhangelsk")
            .birthday(LocalDate.of (1919, 7, 7))
            .aboutUser("I work as an artist")
            .userInterests(Collections.singleton(art))
            .build();

    ArrayList<Interests> interests = new ArrayList<>();

    @Test
    public void testGetInterestsList() {
        interests.add(art);
        when(interestServiceRest.getAllInterests()).thenReturn(interests);
        Assert.assertEquals(200, interestRestControllerPublic.getInterestsList ().getStatusCodeValue());
    }

    @Test
    public void testGetInterestsUserById() {
        interests.add(art);
        when(interestServiceRest.getInterestById(1L)).thenReturn(interests);
        Assert.assertEquals(200, interestRestControllerPublic.getInterestsUserById(1L).getStatusCodeValue());

    }
}
