package day03.restful;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/*
@RestController + @GetMapping+@RequestMapping
	- 해당 클래스 위에 선언
	- 스프링MVC 중에서 controller 역할
	- 서블릿 기능을 제공 받음
	- 컨트롤러 클래스 안에서 주요 사용되는 메소드
        1.@GetMapping("/day03/blue")
        2.@PostMapping("/day03/blue")
        3.@PutMapping("/day03/blue")
        4.@DeleteMapping("/day03/blue")
 */


@RestController //@Controller 동일한 기능+ @ResponseBody 가 자동 추가
@RequestMapping(value="/day03") // 클래스에 매핑url을 정의할 경우, 해당 클래스 안에 메소드들의 공통 url을 생략 가능.
public class RestController5 {
   // 직접 함수를 만듦.
    // 주소와 매핑되고 GET / POST/PUT/DELETE 를 하겠음.

    //1.GET
@GetMapping("/pink")
    public String getBlue(HttpServletRequest request) throws IOException{
        //http://localhost:8080/day03/orange?param1=안녕겟 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //2.POST
@PostMapping("/pink")
    public String postBlue(HttpServletRequest request) throws IOException{
        //확인-> http://localhost:8080/day03/orange  하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //3.PUT
@PutMapping("/pink")
    public String putBlue(HttpServletRequest request) throws IOException{
        //확인-> http://localhost:8080/day03/orange 하고 body입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";

    }
    //4.DELETE
@DeleteMapping("/pink")
    public String deleteBlue(HttpServletRequest request) throws IOException{
        //http://localhost:8080/day03/orange?param1=안녕딜리트 하고 header입력
        //1. 요청
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        return "정상응답";
    }
}
