package ru.team.up.websocket.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.team.up.core.service.AssignedEventsService;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorWebSocketHandler extends TextWebSocketHandler {

    AssignedEventsService assignedEventsService;

    @SneakyThrows
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        log.debug("Выполняется метод handleTextMessage с входным параметром {}", message.toString());

        session.sendMessage(new TextMessage(
                (new JSONArray(
                        assignedEventsService.getAllEventsByModeratorId(Long.parseLong(message.getPayload()))
                )).toString()));

        log.debug("Выполнение метода handleTextMessage завершено");
    }

}
