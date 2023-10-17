package example.day05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
@Autowired//스프링 컨테이너에 빈(객체)주입(전제조건: 컨테이너 등록된 클래스만 가능)
private TodoEntityRepository todoEntityRepository;

    @Transactional// 트랙잭션: 최소 단위 일처리 = > 결과[성공,실패]
                    // import.javax.transaction.Transactional
                    // .save 함수 안에 포함되기 때문에 안써도되긴함. update는 반드시 써줘야되고 나머지는 안써도 되긴하는데 써놓고 시작하자
    public boolean doPost(TodoDto todoDto) {
            //1. DTO를 Entity로 변환
            TodoEntity todoEntity= TodoEntity.builder()
                    .tcontent(todoDto.getTcontent())
                    .tstate(todoDto.isTstate())
                    .build();

            //2.
            todoEntityRepository.save(todoEntity);

            return false;
        }//f()

    public List<TodoDto> doGet() {

            //1.모든 엔티티 호출[select 대체]
        List<TodoEntity> todoEntities=todoEntityRepository.findAll();

            //2.List<TodoEntity>->List<TodoDto> 변환
        List<TodoDto> list= new ArrayList<>();

        //엔티티 리스트에서 하나씩 꺼내기
        todoEntities.forEach((entity)->{
            //3.엔티티를 dto로 변환
            TodoDto todoDto=TodoDto.builder()
                  .tno(entity.getTno())
                  .tcontent(entity.getTcontent())
                  .tstate(entity.isTstate())
                  .build();
            //4.변환된 dto를 리스트에 저장
            list.add(todoDto);
        });



        return list;
        }//f()

@Transactional//import.javax.transaction.Transactional
    public boolean doPut( TodoDto todoDto){

            //1. 수정할 엔티티 찾기
       Optional<TodoEntity>todoEntity= todoEntityRepository.findById(todoDto.getTno());
        //2. Optional 객체에 엔티티 존재 여부 확인[안전성 보장]
             //.isPresent() : Optional
      if( todoEntity.isPresent()){
          //3.Optinal 객체의 엔티티 꺼내기
          TodoEntity updateEntity= todoEntity.get();
          //4. 엔티티 찾았으니 필드 수정 [상태필드만 수정]
          updateEntity.setTstate(todoDto.isTstate());
      };
            return false;
    }

    public boolean doDelete( int tno){

            //1. 삭제할 엔티티 찾기 [삭제할 pk번호]
        todoEntityRepository.deleteById(tno); //전달받은tno 있으니까 findById 사용 가능.

            return false;
    }

}//c
