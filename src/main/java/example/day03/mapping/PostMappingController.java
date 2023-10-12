package example.day03.mapping;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/day03/post")
public class PostMappingController {
    /*
       HttpServletRequest 도 가능
       @PathVarable도 가능
       @ModelAttribute도 가능
     */

   //1.
    @PostMapping("/method1")
    // public String method1(@RequestParam String param1)  -> 테스트: form[param1=유재석] 
    public String method1(@RequestBody String param1){//http://localhost:8080/day03/post/method1 -> 테스트: json{"param1":"유재석","param2":50}
        //테스트->http://localhost:8080/day03/post/method1    //post는 쿼리스트링도가능하지만 비추
        System.out.println("PostMappingController.method1");
        System.out.println("param1 = " + param1);
        return "정상응답";
    }

    //2.
    @PostMapping("/method2")
    // public String method1(@RequestParam String param1)  -> 테스트: form[param1=유재석] 가능
    public String method2(@RequestBody Map<String, String> map){//DTO 쓰는걸 권장하지만, DTO가 없을 때 여러개의 매개변수 매핑이 필요할 때 Map<> 사용.
      //http://localhost:8080/day03/post/method2  테스트: json{"param1":"유재석","param2":50}
       System.out.println("map = " + map);
        System.out.println("PostMappingController.method2");
        return "정상응답";
    }

    //3.
    @PostMapping("/method3")
    // public String method1(@RequestParam String param1)  -> 테스트: form[param1=유재석] 가능
    public String method3( ParamDto paramDto){//DTO 쓰는걸 권장하지만, DTO가 없을 때 여러개의 매개변수 매핑이 필요할 때 Map<> 사용.
        //http://localhost:8080/day03/post/method3 테스트: json{"param1":"유재석","param2":50}
        System.out.println("paramDto = " + paramDto);
        System.out.println("PostMappingController.method3");
        return "정상응답";
    }

}//p
