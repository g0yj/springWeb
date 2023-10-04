package day01.webMvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController // 해당 클래스를 restful로 사용하겠다는 선언
public class WebController {
    @GetMapping("/day01/doget") //http로부터 get 요청 햇을때. 주소를 직접 입력해줘야됨
    public List<WebDto> doGet(){
        WebDao consoleDao = new WebDao();
        List<WebDto> result = consoleDao.doGet();
        return result;

    }
    @PostMapping("/day01/dopost")//http로부터 post 요청했을 때
    public boolean doPost(String title){
        WebDto consoleDto = new WebDto(0, title, LocalDate.now(),true);
        WebDao consoleDao = new WebDao();
        boolean result = consoleDao.doPost(consoleDto);
        return result;
    }


}//c
