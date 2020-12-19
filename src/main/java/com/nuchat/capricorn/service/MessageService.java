package com.nuchat.capricorn.service;

import com.nuchat.capricorn.config.JwtTokenProvider;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.model.*;
import com.nuchat.capricorn.repository.ParticipantsRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    ParticipantsRepository participantsRepository;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    public Conversation createConversation(HttpServletRequest req){
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
        participantsRepository.save(newParticipants);
        return newConversation;
    }
}