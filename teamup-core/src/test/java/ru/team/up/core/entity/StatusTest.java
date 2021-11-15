package ru.team.up.core.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.StatusRepository;



/**
 * Тест сущности статуса мероприятия
 */
@SpringBootTest
class StatusTest{

    @Autowired
    private StatusRepository statusRepository;

    private Status statusTest = Status.builder().status("Examination").build();

    @Test
    void setStatus(){
        statusRepository.save(statusTest);

        Assert.assertTrue(statusRepository.findById(1L).isPresent());
        Assert.assertEquals(statusTest.toString(), statusRepository.findById(1L).orElse(new Status()).toString());

        statusRepository.deleteById(1L);

        Assert.assertFalse(statusRepository.findById(1L).isPresent());
    }
}