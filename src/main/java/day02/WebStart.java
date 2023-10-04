package day02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // Spring MVC 외 추가된 서블릿을 검색해서 컴포넌트 등록 (개발자가 스프링 외 추가 등록된 코드를 스프링이 알 수 있도록 등록하는 기능)
@SpringBootApplication
public class WebStart {
    public static void main(String[] args) {
        SpringApplication.run(WebStart.class);
    }//m

}

/*
    스트링부트의 시작 (서버 켜는 방법)
        1. 해등 클래스의 @SpringBootApplication
            //SPRING MVC ,RESTFUL , 내장톰캣  등등 제공
        2. main 선언[스레드 한개 필요]
        3. main 함수 정의
             SpringApplication.run()



 */