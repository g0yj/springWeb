package example.day04;

import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring mvc 중 해당 클래스를 service 로 사용
public class TodoService {

    // 1.
    public boolean doPost(TodoDto todoDto){
        System.out.println("TodoService.doPost");
        System.out.println("todoDto = " + todoDto);
        return new TodoDao().doPost(todoDto);
    }
    // 2.
    public List<TodoDto> doGet(){
        System.out.println("TodoService.doGet");
        return new TodoDao().doGet();
    }
    // 3.
    public boolean doPut(TodoDto todoDto){
        System.out.println("TodoService.doPut");
        System.out.println("todoDto = " + todoDto);
        return new TodoDao().doPut(todoDto);
    }

    // 4.
    public  boolean doDelete(int tno){
        System.out.println("TodoService.doDelete");
        System.out.println("tno = " + tno);
        return new TodoDao().doDelete(tno);
    }
}

/*
    클래스 호출 방법
    1 객체 생성 후 이용
        클래스명 객체명 = new 클래스명();
        객체명.메소드명();
    2 지역변수 없이 1회성 호출
        new 클래스명().메소드명();
    3 싱글톤 사용
        클래스명.getinstance().메소드명();
    4 단순 static 메소드
        클래스명.메소드명();
    ------------------------------------------
    스프링 컨테이너 [ 스프링 객체들을 관리하는 메모리 공간]에 저장된 빈[객체] 사용
        - 전제조건 : 스프링에 빈 등록이 되어야 한다
        1. 빈 등록 방법
            해당 클래스 위에 mvc 관련 어노테이션 
                ex) Service , @RestController 등등
        2. 그 외 일반적으로 개발자가 만든 클래스를 빈에 등록
            @Component -> 개별로 만든게 빈에 등록 된다
        
    5 스프링 빈 등록 객체의 메소드를 호출할때
        @Autowired      : 의존성 주입[스프링에 미리 등록된 컨테이너 빈 찾아서 주입]
        클래스명 객체명
        객체명.메소드명();








 */