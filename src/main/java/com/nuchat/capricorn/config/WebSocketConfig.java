package com.nuchat.capricorn.config;


import com.nuchat.capricorn.service.UserPresenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final int OUTBOUND_CHANNEL_CORE_POOL_SIZE = 8;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic/","/queue/");
        config.setApplicationDestinationPrefixes("/app");

    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://127.0.0.1:5500")
                .withSockJS()
                .setWebSocketEnabled(false)
                .setSessionCookieNeeded(false);
    }
    @Bean
    public UserPresenceService presenceChannelInterceptor(){
        return new UserPresenceService();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(presenceChannelInterceptor());
    }


    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(OUTBOUND_CHANNEL_CORE_POOL_SIZE);
        registration.interceptors(presenceChannelInterceptor());
    }

    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/*").authenticated();
    }
    


}
