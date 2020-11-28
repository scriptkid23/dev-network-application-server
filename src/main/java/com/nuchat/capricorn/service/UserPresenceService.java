package com.nuchat.capricorn.service;

import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;



@Component
public class UserPresenceService implements ChannelInterceptor,IUserPresenceService{
    @Autowired
    SecurityService securityService;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor stompDetails = StompHeaderAccessor.wrap(message);

        if(stompDetails.getCommand() == null) { return; }

        switch(stompDetails.getCommand()) {
            case CONNECT:
            case CONNECTED:
                toggleUserPresence(stompDetails.getUser().getName().toString(), true);
                break;
            case DISCONNECT:
                toggleUserPresence(stompDetails.getUser().getName().toString(), false);
                break;
            default:
                break;
        }
    }
    private void toggleUserPresence(String userEmail, Boolean isPresent) {
        try {
            User user = securityService.search(userEmail);
            securityService.setIsPresent(user, isPresent);
        } catch (BeansException | CustomException e) {
            e.printStackTrace();
        }
    }

}
