package example.day05과제2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    @GetMapping("/index")
    public Resource getIndex(){

        return new ClassPathResource("templates/phone.html");

    }//f()


    //1. 입력 요청(이름,전화번호) 응답(boolean)
    @PostMapping ("")
    public boolean doPost(@RequestBody PhoneDto dto){
        System.out.println("PhoneController.doPost");
        System.out.println("dto = " + dto);
        boolean result=phoneService.doPost(dto);
        return result;
    }

    @GetMapping("")
    public List<PhoneDto> doGet() {
        System.out.println("PhoneController.doGet");
        List<PhoneDto> result = phoneService.doGet();
        return result;
    }

    @PutMapping("")
    public boolean doPut(@RequestBody PhoneDto dto) {
        System.out.println("PhoneController.doPut");
        System.out.println("put컨트롤러실행");
        boolean result = phoneService.doPut(dto);
        return result;
    }


    @DeleteMapping("")
    public boolean doDelete(@RequestParam int pno) {
        boolean result = phoneService.doDelete(pno);
        return result;
    }


}//c
