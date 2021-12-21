package ru.team.up.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.EventTypeRepository;

/**
 * Тест сущности типа мероприятия
 */
@SpringBootTest
class EventTypeTest extends Assertions{

    @Autowired
    private EventTypeRepository eventTypeRepository;
    private EventType typeTest = EventType.builder().type("Game").build();

    @Test
    void testType(){
        eventTypeRepository.save(typeTest);

        assertTrue(eventTypeRepository.findById(1L).isPresent());
        assertEquals(typeTest.toString(),
                eventTypeRepository.findById(1L).orElse(new EventType()).toString());

        eventTypeRepository.deleteById(1L);
        assertFalse(eventTypeRepository.findById(1L).isPresent());
    }
}
