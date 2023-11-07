package ezenweb.model.repository;

import ezenweb.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Integer> {
    //추상메소드를 이용한 엔티티 검색
    //기본 제공하는 함수 find~, delete~, save~ 등등

    //1. 해당하는 제목의 엔티티 찾기
    BoardEntity findByBtitle(String btitle);
    boolean existsByBtitle(String btitle);
    Page<BoardEntity> findByBtitle(String btitle,Pageable pageable);
    //@Query(value = "select * from board",nativeQuery = true) //=findAll
    // @Query(value = "select * from board where bno=:bno",nativeQuery = true) //=findById
    // @Query(value = "select * from board where btitle=:btitle",nativeQuery = true) //=findByBtitle
    //@Query(value="select * from board where bcontent= :keyword,", nativeQuery = true) //=findByBcontent
    @Query(value = "select * from board where bcontent like %:keyword%", nativeQuery = true) //=제목이 포함된
    Page<BoardEntity> findBySearch(String key, String keyword,Pageable pageable);

}

/*




*/