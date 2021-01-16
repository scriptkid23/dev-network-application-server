package com.nuchat.capricorn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.dto.MessageWebSocketDTO;
import com.nuchat.capricorn.dto.MessageWebSocketResponse;
import com.nuchat.capricorn.dto.NotificationWebSocketDTO;
import com.nuchat.capricorn.dto.NotificationWebSocketResponse;
import com.nuchat.capricorn.model.Messages;
import com.nuchat.capricorn.model.Notification;
import com.nuchat.capricorn.service.MessageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageWebSocketDTO messageWebSocketDTO){

        logger.debug(messageWebSocketDTO.getMessage());
        Messages message = messageService.sendMessage(messageWebSocketDTO);
        MessageWebSocketResponse messageWebSocketResponse = modelMapper.map(message,MessageWebSocketResponse.class);
        messagingTemplate.convertAndSendToUser(
                messageWebSocketDTO.getChannelId(),
                "/queue/messages",messageWebSocketResponse);

    }

    @MessageMapping("/notification")
    public void processNotification(@Payload NotificationWebSocketDTO payload){

        Notification notification = messageService.sendNotification(payload);
        NotificationWebSocketResponse notificationWebSocketResponse = modelMapper.map(notification,NotificationWebSocketResponse.class);

        messagingTemplate.convertAndSendToUser(
                notification.getUser().getEmail(),
                "/queue/notifications",notificationWebSocketResponse);


    }

    // when logged into the system, users will subscribe to their contact list
    // they will subscribe / contact /: id with id as their id
    // whatever the sender conversation they will receive will be
//    @MessageMapping("/contact/:id")
//    public void handleSubscribeContact(@Payload MessageWebSocketDTO messageWebSocketDTO) throws Exception{
//        messagingTemplate.convertAndSendToUser(
//                messageWebSocketDTO.getContactid(),
//                "/queue/contacts",
//                messageWebSocketDTO
//        );
//    }





}
