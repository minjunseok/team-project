package moyeora.myapp.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
//@EnableWebSocketMessageBroker//@EnableWebSocketMessageBroker를 추가하여 웹소켓 활성화.스프링에서 제공하는 내장 메시지 브로커(SimpleBroker)를 사용.
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    //implements WebSocketMessageBrokerConfigurer

    private final WebSocketHandler webSocketHandler;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/sub");
//        //메시지 브로커의 Prefix를 등록하는 부분.
//        // > 클라이언트는 토픽을 구독할 시 /sub 경로로 요청해야 함.
//        config.setApplicationDestinationPrefixes("/pub");
//        //도착 경로에 대한 Prefix를 설정
//        //> 클라이언트에서 메시지 발행 시 해당 메시지 매핑에 대한 접두사로 사용됨.
//    }

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
//        //웹소켓 연결에 필요한 Endpoint를 지정함과 동시에
//        // setAllowedOriginPatterns 부분을 애스터리스크(*)로 설정하여 모든 출처에 대한 Cors 설정.
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
