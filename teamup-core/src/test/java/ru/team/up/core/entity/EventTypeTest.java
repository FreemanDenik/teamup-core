package ru.team.up.core.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.EventTypeRepository;

/**
 * Тест сущности типа мероприятия
 */
@SpringBootTest
class EventTypeTest{

    @Autowired
    private EventTypeRepository eventTypeRepository;
    private EventType typeTest = EventType.builder().type("Game").build();

    @Test
    void testType(){
        eventTypeRepository.save(typeTest);

        Assert.assertTrue(eventTypeRepository.findById(1L).isPresent());
        Assert.assertEquals(typeTest.toString(),
                eventTypeRepository.findById(1L).orElse(new EventType()).toString());

        eventTypeRepository.deleteById(1L);
        Assert.assertFalse(eventTypeRepository.findById(1L).isPresent());
    }
}
