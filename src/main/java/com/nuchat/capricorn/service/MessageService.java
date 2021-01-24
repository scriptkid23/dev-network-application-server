package com.nuchat.capricorn.service;

import com.google.gson.Gson;
import com.nuchat.capricorn.config.JwtTokenProvider;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.dto.*;
import com.nuchat.capricorn.model.*;
import com.nuchat.capricorn.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private ModelMapper modelMapper;
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
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserContactRepository userContactRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

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


    public Messages sendMessage(MessageWebSocketDTO messageWebSocketDTO){
        Conversation conversation = conversationRepository.getCoversationDetail(messageWebSocketDTO.getChannelId());
        User user = userRepository.findByEmail(messageWebSocketDTO.getSender());
        Messages messages = new Messages();
        messages.setMessage(messageWebSocketDTO.getMessage());
        messages.setMessage_type(MessageType.TEXT);
        messages.setConversation(conversation);
        messages.setCreated_at(new Date());
        messages.setUser(user);
        messagesRepository.save(messages);
        return messages;
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
    public List<ListMessageLogDTO> getListMessageLog(HttpServletRequest req){
        return conversationRepository.getListMessageLog(
                securityService.whoami(req).getId()
        );
    }
    public List<ResponseListMessageLogDTO> getListMessageGroupLog(HttpServletRequest req){
        List<ListMessageLogGroupDTO> listMessageLogGroup = conversationRepository.getListMessageLogGroup(
                securityService.whoami(req).getId()
        );
//        Gson gson = new Gson();
//        logger.info(gson.toJson(listMessageLogGroup));
        ResponseListMessageLogDTO[] result = modelMapper.map(listMessageLogGroup,ResponseListMessageLogDTO[].class);
        for (ResponseListMessageLogDTO list :
                result) {
            List<MemberOfConversationDTO> listMemberOfConversation = conversationRepository.getMemberOfConversation(list.getConversation_id());
            MemberOfConversationImpl[] result_ = modelMapper.map(listMemberOfConversation,MemberOfConversationImpl[].class);
            for (MemberOfConversationImpl value:result_
                 ) {
                list.setMember(Arrays.asList(result_));
            }
        }
        return Arrays.asList(result);
    }

    public List<ListNotificationDTO> getListNotification(HttpServletRequest req){
            return notificationRepository.getListNotification(
                    securityService.whoami(req).getId()
            );
    }
    public Notification sendNotification(NotificationWebSocketDTO payload){
        User receiver = userRepository.findByEmail(payload.getEmail());
        Notification notification = new Notification(receiver,false,payload.getSender(),payload.getInvitation_message());
        notificationRepository.save(notification);
        return notification;
    }

    public void acceptFriendRequest(HttpServletRequest req,AcceptFriendRequestDTO payload){
        UserContact sender = new UserContact();
        UserContact receiver = new UserContact();
        User user_sender = userRepository.findByEmail(payload.getSender_from());
        User user_receiver = securityService.whoami(req);

        // Insert sender to database with table user_contact
        sender.setFirst_name(user_receiver.getFirst_name());
        sender.setLast_name(user_receiver.getLast_name());
        sender.setUser(user_sender);
        sender.setContact(modelMapper.map(user_receiver,Contacts.class));
        userContactRepository.save(sender);

        // continues
        receiver.setFirst_name(user_sender.getFirst_name());
        receiver.setLast_name(user_sender.getLast_name());
        receiver.setUser(user_receiver);
        receiver.setContact(modelMapper.map(user_sender,Contacts.class));
        userContactRepository.save(receiver);

       Optional<Notification> notification = notificationRepository.findById(payload.getId());
       notificationRepository.delete(notification.get());


    }

}