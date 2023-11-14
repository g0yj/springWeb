package ezenweb.config;

import ezenweb.controller.ChattingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration//빈등록
@EnableWebSocket //웹소켓 연결. 클라이언트소켓이 열려도 연동x
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired private ChattingController chattingController;

    @Override//사용할 서버소켓들의 매핑 주소와 접근 제한 설정
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chattingController,"/chat");

    }//f()
}//c
