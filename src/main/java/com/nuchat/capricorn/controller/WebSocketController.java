package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(SendEmailService.class);
    private final SimpMessagingTemplate template;

    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void sendMessage(String message) throws  Exception{
        System.out.println(message);
        this.template.convertAndSend("/message",  message);
    }
    // handle of status after user login into system
    @MessageMapping("/join")
    public void join() throws Exception{
        logger.debug("Join");
        template.convertAndSend(
                "/observable/join"
        );
    }
    // Observable list message log was contacted
    @MessageMapping("/contact")
    public void contact() throws Exception{
        logger.debug("Contact");
        template.convertAndSend(
                "/observable/contact"
        );
    }
    // Observable list notification between of two user
    @MessageMapping("/notification")
    public void notification() throws Exception{
        logger.debug("Notification");
        template.convertAndSend(
                "/observable/notification"
        );
    }




}
