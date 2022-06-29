package ru.team.up.input.controller.publicController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.input.service.InterestServiceRest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class InterestRestControllerPublicTest {

    @Mock
    private InterestServiceRest interestsServiceRest;

    @Mock
    private MonitorProducerService monitorProducerService;

    @InjectMocks
    private InterestRestControllerPublic interestRestControllerPublic =
            new InterestRestControllerPublic(interestsServiceRest, monitorProducerService);

    private Interests art;
    private List<Interests> interests;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);
        interests = new ArrayList<>();

        art = Interests.builder()
                .title("Искусство")
                .shortDescription("графика, скульптура, живопись")
                .build();

        interests.add(art);
    }


    @Test
    void getInterestsList() {
        when(interestsServiceRest.getAllInterests()).thenReturn(interests);
        // OK
        assertEquals(1, interestRestControllerPublic.getInterestsList().getInterestsDtoList().size());
        // Not OK
        assertNotEquals(2, interestRestControllerPublic.getInterestsList().getInterestsDtoList().size());
    }

    @Test
    void getInterestsUserById() {
        when(interestsServiceRest.getInterestById(1L)).thenReturn(art);
        // ОК
        assertEquals("Искусство",
                interestRestControllerPublic.getInterestsUserById(1L).getInterestsDto().getTitle());
        // Not OK
        assertNotEquals("Спорт",
                interestRestControllerPublic.getInterestsUserById(1L).getInterestsDto().getTitle());
    }
}