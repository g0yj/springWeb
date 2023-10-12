package example.day03.restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
@ Controller +  @RequestMapping
	- 해당 클래스 위에 선언
	- 스프링MVC 중에서 controller 역할
	- 서블릿 기능을 제공 받음
	- 컨트롤러 클래스 안에서 주요 사용되는 메소드
		1.@RequestMapping
			- 외부 HTTP 요청으로부터 해당 함수와 매핑해주는 어노테이션
			- @RequestMapping(value="url주소",method =RequestMethod.PUT )
				-!! 프로젝트 내 절대적으로 매핑url과 http함수 모두 동일하게 정의할 수 없음. ( GET이나 POST 같이 다른 건 괜찮. get 여러 개일 시 동일한 주소 안됨)
 */


@Controller // 해당 클래스를 스프링MVC 중 컨트롤러 객체로 사용(클래스 위에 선언) // 스프링 컨트롤러 객체를 빈에 등록
public class RestController1 {
   // 직접 함수를 만듦.
    // 주소와 매핑되고 GET / POST/PUT/DELETE 를 하겠음.

    //1.GET
    @RequestMapping(value="/day03/black",method = RequestMethod.GET)  //함수마다 주소값을 다르게 할 수 있음
    public void getBlack(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //http://localhost:8080/day03/black?param1=안녕겟 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("정상응답");

    }
    //2.POST
    @RequestMapping(value="/day03/black",method = RequestMethod.POST)
    public void postBlack(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //확인-> http://localhost:8080/day03/black  하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("정상응답");

    }
    //3.PUT
    @RequestMapping(value="/day03/black",method = RequestMethod.PUT)
    public void putBlack(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //확인-> http://localhost:8080/day03/black 하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("정상응답");

    }
    //4.DELETE
    @RequestMapping(value="/day03/black",method = RequestMethod.DELETE)
    public void deleteBlack(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //http://localhost:8080/day03/black?param1=안녕딜리트 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("정상응답");

    }
}
