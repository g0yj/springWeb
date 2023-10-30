package ezenweb.model.repository;

import ezenweb.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {

                                            //조작할entity명, pk
                                            //int의 래퍼클래스인 integer사용
    //인터페이스임->추상메소드를 활용하자!
    //-이메일 이용한 엔티티 검색 select*from member where memail=변수
    //반환자료형 추상메소드(매개변수)
        //1.필드명을 이용한 엔티티 검색: 반환엔티티 findBy필드명(매개변수);
        //2.필드명을 이용한 엔티티 존재 여부 검색: boolean existsBy필드명(매개변수);

            //1. 동일한 이메일 있을 때 '엔티티' 반환 없을 때 'null'반환
            //MemberEntity findBy필드명(매개변수);
    MemberEntity findByMemail(String memail);

             //2. 동일한 이메일 있을 때 'Optional'반환 없을 때 'Optional' 반환
             //Optional<MemberEntity> findByMemail( String memail ); // select * from member where memail = 변수
    //Optional<MemberEntity> findByEmail(String email);

            //3. 동일한 이메일 있을 때 'true'없을 때 false 반환
    boolean existsByMemail(String memail);

            //4. 조건에 논리가 있을 때 (and/or) ex.이메일과 이름이 같을 때
            //select*from member where mname=변수 and memail=변수;
    //MemberEntity findByMnameAndMemail(String mname,String memail);

            //5. 논리 많을 때 @Query 사용해서 실제 SQL문과 동일하게 처리할 수 있는 방법 있음.
}

