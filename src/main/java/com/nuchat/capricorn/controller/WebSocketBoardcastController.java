package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.StatusUser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketBoardcastController {

    @MessageMapping("/workspace")
    @SendTo("/topic/workspace")
    public StatusUser send(StatusUser chatMessage) throws Exception {
        return new StatusUser(chatMessage.getFrom(), chatMessage.getText(), "ALL");
    }

}
