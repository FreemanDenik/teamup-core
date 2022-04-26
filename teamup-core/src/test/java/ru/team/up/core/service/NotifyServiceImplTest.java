package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import ru.team.up.dto.NotifyDto;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class NotifyServiceImplTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private NotifyService notifyService = new NotifyServiceImpl();

    NotifyDto notifyDtoTest = new NotifyDto();

    Disposable disposableTest;

    ClientResponse clientResponseTest = ClientResponse.create(HttpStatus.CREATED).build();

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNotify() {
        // См. тест callNotifyController();
    }

    @Test
    void callNotifyController() {
        // ??
    }
}