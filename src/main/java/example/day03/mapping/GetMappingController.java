package example.day03.mapping;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController // @Controller +@ResponseBody 효과
@RequestMapping("/day03/get") //해당 클래스의 URL 매핑 정의 [공통 url]
public class GetMappingController {

    //1.HttpServletRequest 객체 이용한 get 메소드 url쿼리스트링의 매개변수 요청
    @GetMapping("/method1")
    public String method1(HttpServletRequest request){ // http://localhost:80/day03/get/method1?param1=안녕겟
        System.out.println("GetMappingController.method1");//soutm
        String param1= request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return  "정상응답";
            // return "정상응답" 인 이유 ? - @ResponseBody <- (resp.getWriter().pringln("정상응답")을 제공
    }//f()
    //2. @RequestParam URL 매개변수 쿼리스트링[URL?매개변수=값] 매개변수 매핑
    @GetMapping("/method2")
    public String method2(@RequestParam String param1){// http://localhost:80/day03/get/method2?param1=안녕겟
        System.out.println("GetMappingController.method2");
        System.out.println("param1 = " + param1);//soutv
        return  "정상응답";

        // return "정상응답" 인 이유 ? - @ResponseBody <- (resp.getWriter().pringln("정상응답")을 제공
    }//f()

    //3. RequestParam 두개 이상
    @GetMapping("/method3")
    public String method3(@RequestParam String param1, @RequestParam int param2){ // http://localhost:80/day03/get/method4?param1=유재석&param2=50
        System.out.println("GetMappingController.method3");//soutm
        System.out.println("param1 = " + param1 + ", param2 = " + param2);//soutp
        return "정상응답";

    }

    //4. 여러개 매개변수를 DTO로 자동 변환 매핑
        // DTO로 받을 수 있기 때문에 여러 필드를 하나씩 받을 필요도 없고 받은 걸 객체화 해서 전달할 필요 없음
        //@RequestParam를 사용하지 않거나 @ModelAttribute(5번)를 사용
    @GetMapping("/method4")
    public String method4(ParamDto paramDto){ // http://localhost:80/day03/get/method4?param1=유재석&param2=50
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
    //5. 여러개 매개변수를 DTO로 자동 변환 매핑
    @GetMapping("/method5")
    public String method5(@ModelAttribute ParamDto paramDto){// http://localhost:80/day03/get/method4?param1=유재석&param2=50
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }

    //6. 경로매개변수(URL/값1/값2...) [VS 쿼리스트링]
                                            //<a href=""></a>
        //ServerSocket("URL/{매개변수}/{매개변수}")
    @GetMapping("/method6/{param1}/{param2}")
    public String method6(@PathVariable("param1") String param1, @PathVariable("param2") int param2){
        // PathVariable 방식 http://localhost:80/day03/get/method6/유재석/50
        return "정상응답";

    }

    //7. 경로매개변수 방식 DTO 지원. (4번이랑 동일)
    @GetMapping("/method7/{param1}/{param2}")
    public String method7(ParamDto paramDto){
       // PathVariable 방식    http://localhost:80/day03/get/method6/유재석/50
        System.out.println("GetMappingController.method7");
        System.out.println("paramDto = " + paramDto);
        return "정상응답";

    }

    //8. 경로매개변수 방식도 DTO 지원 (5번과 동일)
    @GetMapping("/method8/{param1}/{param2}")
    public String method8(@ModelAttribute ParamDto paramDto){
        //테스트 -> http://localhost:80/day03/get/method6/유재석/50
        System.out.println("paramDto = " + paramDto);
        System.out.println("GetMappingController.method8");
        return "정상응답";

    }


}
