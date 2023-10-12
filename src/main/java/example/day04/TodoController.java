package example.day04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;


// 스프링 Bin : 스프링 컨테이터에 저장된 객체

@RestController
@RequestMapping("/todo")

public class TodoController {

    @Autowired  // 미리 등록된 스프링 컨테이너에서 빈 찾아서 주입
    private TodoService todoService;    // 서비스객체



    // REST : http 기반으로 get , post , put delete 메소드 이용한 웹 서비스

    // 1. C
    @PostMapping("")
    public boolean doPost(@RequestBody TodoDto todoDto){// 요청 매개변수 : 입력받은 정보들
                        //@RequestBody : http body // json 형식으로 요청 매핑
        System.out.println("TodoController.doPost");
        System.out.println("todoDto = " + todoDto);
        return todoService.doPost(todoDto);
    }

    // 2. R

    @GetMapping("")
    public List<TodoDto> doGet(){
        System.out.println("TodoController.doGet");

        return todoService.doGet();
    }

    // 3. U
    @PutMapping("")
    public boolean doPut(@RequestBody TodoDto todoDto){
        System.out.println("TodoController.doPut");
        System.out.println("todoDto = " + todoDto);
        return todoService.doPut(todoDto);
    }

    // 4. D
    @DeleteMapping("")
    public boolean doDelete(@RequestParam int tno){//@RequestParam  쿼리스트링에서 매개변수 요청할때 사용
        System.out.println("TodoController.doDelete");
        System.out.println("tno = " + tno);
        return todoService.doDelete(tno);
    }

    // 5.
    @GetMapping("/index")
    public Resource getIndex(){

        return new ClassPathResource("templates/todo.html");
    }



}
