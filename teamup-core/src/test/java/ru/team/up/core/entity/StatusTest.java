package ru.team.up.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.StatusRepository;



/**
 * Тест сущности статуса мероприятия
 */
@SpringBootTest
class StatusTest extends Assertions {

    @Autowired
    private StatusRepository statusRepository;

    private Status statusTest = Status.builder().status("Examination").build();

    @Test
    void setStatus() {
        statusRepository.save(statusTest);

        assertTrue(statusRepository.findById(1L).isPresent());
        assertEquals(statusTest.toString(), statusRepository.findById(1L).orElse(new Status()).toString());

        statusRepository.deleteById(1L);

        assertFalse(statusRepository.findById(1L).isPresent());
    }
}