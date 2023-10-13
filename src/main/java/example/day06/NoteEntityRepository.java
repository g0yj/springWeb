package example.day06;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //스프링 컨테이너에 등록[왜?? 객체 관리를 스프링이 하기 때문에. 내가 객체를 만들게 아니니까. 다른 곳에서 객체를 써야되니까!]
public interface NoteEntityRepository extends JpaRepository<NoteEntity,Integer> {

}
