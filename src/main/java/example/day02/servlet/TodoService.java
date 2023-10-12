package example.day02.servlet;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TodoService {//서비스: 구현해야하는 기능들의 실제 처리를 담당

    //1. 등록 서비스
    public void register(TodoDto todoDto){
        System.out.println("ToService.register");//soutm
    }//f()

    //2. 출력 서비스[DAO없이 테스트 목적의 임의의 데이터를 반환하는 리스트]
    public List<TodoDto> getList() {

        //2. 스트림 문법을 사용했을 때
        //List<TodoDto> todoDtos = IntStream.range(0,10).mapToObj(i->{리스트에 저장할 객체를 리턴}).collect(Collectors.toList());

        //IntStream.range(시작정수,끝정수)
        List<TodoDto> todoDtos = IntStream.range(0, 10).mapToObj(i -> {
            TodoDto todoDto = TodoDto.builder()
                    .tno((long) i).title("Todo.." + i).dueDate(LocalDate.now())
                    .build();
            return todoDto;
        }).collect(Collectors.toList());
        return todoDtos;
    }
    }//f()






    /*
        //1. 스트림 문법을 사용하지 않을 때
        List<TodoDto> list = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            TodoDto todoDto = TodoDto.builder()
                    .tno((long) i).title("Todo.." + i).dueDate(LocalDate.now()).build();
            list.add(todoDto);
        }
        return list;
    */