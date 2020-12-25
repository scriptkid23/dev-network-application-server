package com.nuchat.capricorn.service;

import com.nuchat.capricorn.config.JwtTokenProvider;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.dto.MessageWebSocketDTO;
import com.nuchat.capricorn.model.*;
import com.nuchat.capricorn.repository.ConversationRepository;
import com.nuchat.capricorn.repository.MessagesRepository;
import com.nuchat.capricorn.repository.ParticipantsRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityService securityService;

    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ParticipantsRepository participantsRepository;
    @Autowired
    MessagesRepository messagesRepository;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    public Conversation createConversation(HttpServletRequest req, List<User> receiver){
        User personCreateConversation = securityService.whoami(req);
        UUID channelId = UUID.randomUUID();
        Date createAt = new Date();
        Conversation newConversation = new Conversation(
                channelId.toString(),
                channelId.toString(),
                createAt,
                personCreateConversation
        );

        Participants newParticipants = new Participants(ParticipantType.SINGLE,new Date(),personCreateConversation,newConversation);
        conversationRepository.save(newConversation);
        participantsRepository.save(newParticipants);
        for(User list_receiver: receiver){
            participantsRepository.save(new Participants(ParticipantType.SINGLE,new Date(),list_receiver,newConversation));
        }
        return newConversation;
    }
    public Conversation getConversationDetail(String channelId){
        return conversationRepository.getCoversationDetail(channelId);
    }
    public Messages sendMessage(HttpServletRequest req, MessageWebSocketDTO message){
        Conversation conversation = conversationRepository.getCoversationDetail(message.getChannelId());
        User user = securityService.whoami(req);
        Messages messages = new Messages();
        messages.setMessage(message.getMessage());
        messages.setMessage_type(MessageType.TEXT);
        messages.setConversation(conversation);
        messages.setUser(user);
        messagesRepository.save(messages);
        return  messages;
    }
}