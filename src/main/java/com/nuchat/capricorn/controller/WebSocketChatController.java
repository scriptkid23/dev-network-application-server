package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.utils.ActiveUserChangeListener;
import com.nuchat.capricorn.utils.ActiveUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;

@RestController
public class WebSocketChatController implements ActiveUserChangeListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketChatController.class);

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    private ActiveUserManager activeUserManager;

    @PostConstruct
    private void init() {
        activeUserManager.registerListener(this);
    }

    @PreDestroy
    private void destroy() {
        activeUserManager.removeListener(this);
    }

    @GetMapping("/sockjs-message")
    public String getWebSocketWithSockJs() {
        return "sockjs-message";
    }


    @Override
    public void notifyActiveUserChange() {
        Set<String> activeUsers = activeUserManager.getAll();
        webSocket.convertAndSend("/topic/active", activeUsers);
    }

}
