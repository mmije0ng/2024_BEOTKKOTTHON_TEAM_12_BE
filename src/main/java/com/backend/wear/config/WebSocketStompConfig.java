package com.backend.wear.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// stomp 연결 config

@RequiredArgsConstructor
@Slf4j
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    private final FilterChannelInterceptor filterChannelInterceptor;

    // sockJS Fallback을 이용해 노출할 endpoint 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        // 웹소켓이 handshake를 하기 위해 연결하는 endpoint
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new StompHandshakeInterceptor()) // StompHandshakeInterceptor 추가
                .withSockJS();

        // SockJS 지원 안 할시
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new StompHandshakeInterceptor());
    }

    //메세지 브로커에 관한 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // 토픽 구독 시 /sub 로 요청
        registry.enableSimpleBroker("/sub"); //구독
        // 도착 경로에 대한 prefix 설정
        registry.setApplicationDestinationPrefixes("/pub"); //메시지 송신
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(filterChannelInterceptor); // FilterChannelInterceptor 추가
    }
}