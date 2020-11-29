package com.nuchat.capricorn.config;

import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketAuthenticationConfig.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    SecurityService securityService;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors((new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
                logger.info("************ STOMP COMMAND *****"+accessor.getCommand());
                logger.info("STOMP access destination "+accessor.getDestination());
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    logger.debug("Authorization:{}",authorization);
                    String accessToken = authorization.get(0).split(" ")[1];
                    logger.info("Access Token ---- " + accessToken);

                    String username = jwtTokenProvider.getUsername(accessToken);

                    UserDetails userDetails = myUserDetails.loadUserByUsername(jwtTokenProvider.getUsername(accessToken));
                    if(username != null){
                        logger.debug("security context was null, so authorizating user");
                        if (jwtTokenProvider.validateToken(accessToken)) {
                            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                            logger.info("authorizated user '{}', setting security context", username);
                            if(SecurityContextHolder.getContext().getAuthentication() == null){
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                accessor.setUser(authentication);
                            }
                            if(StompCommand.SEND.equals(accessor.getCommand())){
                                boolean isSent = channel.send(message);
                                logger.info("Message sent "+isSent);
                                if(isSent) return message;
                            }
                        }
                    }
                }
                else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if(Objects.nonNull(authentication)){
                        logger.info("Disconnected Auth : " + authentication.getName());
                    }
                    else {
                        logger.info("Disconnected Sess : " + accessor.getSessionId());
                    }
                }
                return message;
            }
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent ){
                StompHeaderAccessor stompDetails = StompHeaderAccessor.wrap(message);
                if(stompDetails.getCommand() == null){
                    logger.warn("postSend null command");
                    return;
                }
                switch (stompDetails.getCommand()){
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
            private void toggleUserPresence(String email, Boolean isPresend){
                try{
                    User user = securityService.search(email);
                    securityService.setIsPresent(user,isPresend);
                }
                catch (CustomException e){
                    e.printStackTrace();
                }
            }
        }));
    }
}
