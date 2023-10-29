package ezenweb.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringMvcViewConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry); //주석-> 기존의 view 아키텍처 사용 안함

        registry.addViewController( "/{spring:\\w+}").setViewName("forward:/");
                                                    //spring : resources폴더 의미함.
        registry.addViewController( "/**/{spring:\\w+}").setViewName("forward:/");


        //spring 2.6이상 버전에서는 해당 패턴 경로 못 찾음 (우린 지금 springboot: 2.7.17 버전 사용 중임...!)
        //해결-> application.properties에 추가로 적어줘야됨. (spring.mvc.pathmatch.matching-strategy = ant_path_matcher)
        registry.addViewController( "/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
            // **/ :(와일드카드) 경로 설정 방법임
            //공문 참조!!  **/**/**/ 이런식으로 최대 갯수 정해줘야 새로고침해도 주소 오류 안남. 명세서 작성해서 전달해야됨.


    }
}

/*
    원래 스프링 MVC 아키턱처에서는 CONTROLLER가 VIEW 반환 하는 작업을 진행함.
    그러나 리액트와 통합할 때 문제가 발생함.
    리액트는 라우터를 통해 (Link,get ) 모두 사용하나 스프링은 get만 사용함.
    스프링 안에 리액트가 포함되는데 ...!! 이렇게 되면 get매핑 시 스프링과 우선순위가 되기 때문에 문제 발생.
    get 요청 시 view를 찾을 때 controller가 resources로 이동할 수 있도록 매핑 잡아주자

    //1. 스프링 설정 클래스 생성
    //2. extend WebMvcConfigurerAdapter : MVC 아키텍처 설정 커스텀해주는 클래스
    //3. 오른쪽 클릭->생성->오버라이딩
    //4. addViewController 메소드 오버라이딩/재정의 하기


*/