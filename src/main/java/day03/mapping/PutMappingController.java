package day03.mapping;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/day03/put")
public class PutMappingController {

    //1.
    @PutMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto paramDto){
        return paramDto; //자동으로 DTO를 application/json타입의 형식으로 paramDto를 응답 // DTO->JSON

    }

    //2.
    @PutMapping("/method2")
    public Map<String,String> method2(@RequestBody Map<String, String> map){
        return map;//자동으로 map를 application/json타입의 형식으로 map를 응답 //Map->JSON
    }


}
