package example.day05;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//*엔티티 조작하는 (인터페이스) 리모콘 만들기
@Repository//스프링 컨테이너에 빈 등록 , 이 클래스는 상속 받아 사용할거라 생략할 수 있음.
public interface TodoEntityRepository extends JpaRepository<TodoEntity, Integer> {
}
