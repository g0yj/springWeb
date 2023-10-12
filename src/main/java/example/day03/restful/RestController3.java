package example.day03.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/*
@RestController +@RequestMapping
	- 해당 클래스 위에 선언
	- 스프링MVC 중에서 controller 역할
	- 서블릿 기능을 제공 받음
	- 컨트롤러 클래스 안에서 주요 사용되는 메소드
		1.@RequestMapping
			- 외부 HTTP 요청으로부터 해당 함수와 매핑해주는 어노테이션
			- @RequestMapping(value="url주소",method =RequestMethod.PUT )
				-!! 프로젝트 내 절대적으로 매핑url과 http함수 모두 동일하게 정의할 수 없음. ( GET이나 POST 같이 다른 건 괜찮. get 여러 개일 시 동일한 주소 안됨)

 */


@RestController //@Controller 동일한 기능+ @ResponseBody 가 자동 추가

public class RestController3 {
   // 직접 함수를 만듦.
    // 주소와 매핑되고 GET / POST/PUT/DELETE 를 하겠음.

    //1.GET
    @RequestMapping(value="/day03/red",method = RequestMethod.GET)  //함수마다 주소값을 다르게 할 수 있음

    public String getRed(HttpServletRequest request) throws IOException{
        //http://localhost:8080/day03/orange?param1=안녕겟 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //2.POST
    @RequestMapping(value="/day03/red",method = RequestMethod.POST)

    public String postRed(HttpServletRequest request) throws IOException{
        //확인-> http://localhost:8080/day03/orange  하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //3.PUT
    @RequestMapping(value="/day03/red",method = RequestMethod.PUT)

    public String putRed(HttpServletRequest request) throws IOException{
        //확인-> http://localhost:8080/day03/orange 하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //4.DELETE
    @RequestMapping(value="/day03/red",method = RequestMethod.DELETE)

    public String deleteRed(HttpServletRequest request) throws IOException{
        //http://localhost:8080/day03/orange?param1=안녕딜리트 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";
    }
}
