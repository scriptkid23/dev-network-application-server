package com.nuchat.capricorn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.dto.*;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.Messages;
import com.nuchat.capricorn.model.Notification;
import com.nuchat.capricorn.repository.ConversationRepository;
import com.nuchat.capricorn.service.MessageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationRepository conversationRepository;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageWebSocketDTO messageWebSocketDTO){

        logger.debug(messageWebSocketDTO.getMessage());
        Messages message = messageService.sendMessage(messageWebSocketDTO);
        Conversation conversation = conversationRepository.getCoversationDetail(messageWebSocketDTO.getChannelId());
        MessageWebSocketResponse messageWebSocketResponse = modelMapper.map(message,MessageWebSocketResponse.class);
        messageWebSocketResponse.setChannel_id(messageWebSocketDTO.getChannelId());
        messageWebSocketResponse.setMember(conversationRepository.getMemberOfConversation(conversation.getId()));
        messageWebSocketResponse.setTitle(conversation.getTitle());
        logger.debug(messageWebSocketDTO.getChannelId());
        List<ListUserIdOfConversation> listUserId = conversationRepository.getListUserIdOfConversation((messageWebSocketDTO.getChannelId()));
        for (ListUserIdOfConversation userId:listUserId
        ) {
            logger.info(userId.getUser_id().toString());
            messagingTemplate.convertAndSendToUser(
                    userId.getUser_id().toString(),
                    "/queue/chats",messageWebSocketResponse
            );
        }
        messagingTemplate.convertAndSendToUser(
                messageWebSocketDTO.getChannelId(),
                "/queue/messages",messageWebSocketResponse);

    }

    @MessageMapping("/notification")
    public void processNotification(@Payload NotificationWebSocketDTO payload){

        Notification notification = messageService.sendNotification(payload);
        NotificationWebSocketResponse notificationWebSocketResponse = modelMapper.map(notification,NotificationWebSocketResponse.class);

        messagingTemplate.convertAndSendToUser(
                notification.getUser().getId().toString(),
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
