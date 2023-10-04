package day03.mapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/day03/delete")
public class DeleteMappingController {
    //1.
    @DeleteMapping("/method1")
    public boolean method1(@RequestParam String param1){

        System.out.println("DeleteMappingController.method1");
        System.out.println("param1 = " + param1);
        return true;

    }

    //2.
    @DeleteMapping("/method2")
    public boolean method2(@RequestParam Map<String,String> map){
        System.out.println("map = " + map);
        return false;
    }

    //3.
    @DeleteMapping("/method3")
    public boolean method3(ParamDto paramDto){
        System.out.println("paramDto = " + paramDto);
        return false;
    }
}
