package com.nuchat.capricorn.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuchat.capricorn.controller.SecurityController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    final Cache authCache;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    public WebSocketConfig(CacheManager cacheManager){
        this.authCache = cacheManager.getCache("AuthCache");
    }


    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://127.0.0.1:5500");
        registry.addEndpoint("/ws").setAllowedOrigins("http://127.0.0.1:5500").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/topic","/queue");
        registry.setApplicationDestinationPrefixes("/app");// Enables a simple in-memory broker
        registry.setUserDestinationPrefix("/topic");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                logger.info("************ STOMP COMMAND *****"+accessor.getCommand());
                logger.info("STOMP access destination "+accessor.getDestination());
                logger.info("STOMP command CONNECT equals: "+StompCommand.CONNECT.equals(accessor.getCommand()));
                logger.info("STOMP command SEND equals: "+StompCommand.SEND.equals(accessor.getCommand()));
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    logger.info("CONNECT");
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    List<String> Username = accessor.getNativeHeader("Username");
                    logger.info("Authorization:{}",authorization);
                    logger.info("username:{}", Username);
                    UUID accessToken = UUID.fromString(authorization.get(0));
                    String username = Username.get(0);
                    logger.info("access token:{}",accessToken);
                    logger.info("username:{}",username);
                    WebSocketAuthInfo webSocketAuthInfo = getWebSocketAuthInfo(accessToken);
                    if(webSocketAuthInfo.getAuthToken() != null){
                        UserDetails userDetails = myUserDetails.loadUserByUsername(username);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        accessor.setUser(authentication);
                    }
                    if(StompCommand.SEND.equals(accessor.getCommand())){
                        boolean isSent = channel.send(message);
                        logger.info("Message sent "+isSent);
                        if(isSent) return message;
                    }


                }
                else if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (Objects.nonNull(authentication)) {
                        logger.info("Disconnected Auth : " + authentication.getName());
                    } else {
                        logger.info("Disconnected Sess : " + accessor.getSessionId());
                    }

                }
                return message;
            }
        });
    }
    WebSocketAuthInfo getWebSocketAuthInfo(UUID token){
        if(token == null){
            return  null;
        }
        Cache.ValueWrapper cacheResult = authCache.get(token);

        if(cacheResult == null){
            return  null;
        }
        authCache.evict(token);
        return (WebSocketAuthInfo)cacheResult.get();
    }
}
