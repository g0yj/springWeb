package ezenweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication//스프링부트 실행 해주는 기능 주입 (SpringWeb)
@EnableJpaAuditing//
public class EzenAppStart {
    public static void main(String[] args) {
        SpringApplication.run(EzenAppStart.class);
        //SringApplication : 외부라이브러리(우리가만든거X)를 gradle을 통해 가져와 주입 받음.
    }//m
}//c
