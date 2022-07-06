package ru.team.up.websocket.endpoint;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint
public class ModeratorAssignEndpoint {
    private Session session;

    public ModeratorAssignEndpoint(URI uri) throws DeploymentException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, uri);
        log.debug("Подключение по URI {} произведено", uri.toString());
    }

    @OnOpen
    public void onOpen(Session session) {
        log.debug("Открытие websocket в ModeratorAssignEndpoint");
        this.session = session;
        log.debug("websocket в ModeratorAssignEndpoint открыт");
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        log.debug("Закрытие websocket в ModeratorAssignEndpoint");
        this.session = null;
    }

    /**
     * Метод отправки сообщения
     * @param message текстовое сообщение для отправки
     */
    public void sendMessage(String message) {
        log.debug("Отправка сообщения...");
        this.session.getAsyncRemote().sendText(message);
        log.debug("Сообщение отправлено");
    }

    @OnMessage
    public void onMessage(String message) {
        log.debug("Получено сообщение в ModeratorAssignEndpoint:\n{}\n", message);
    }
}
