package example.day05과제2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 엔티티 조작하는 인터페이스
public interface PhoneEntityRepository extends JpaRepository<PhoneEntity,Integer>{
}
