package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.MessageWebSocketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/hello")
//    public void processMessage(@Payload MessageWebSocketDTO messageWebSocketDTO)throws Exception{
//        messagingTemplate.convertAndSendToUser(
//                messageWebSocketDTO.getRoomid(),
//                "/queue/messages",
//                messageWebSocketDTO
//        );
//    }
}
