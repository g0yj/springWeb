package ezenweb.model.repository;

import ezenweb.model.entity.BaseTime;
import ezenweb.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,String> {
    //1. 모든 제품의 제품명과 재고 수 검색
    @Query(value="select pname, pstock from product",nativeQuery = true)
    List <Map<Object,Object> > findByBarChart();


    //2. 모든 카테고리의 제품 수 검색   (innerjoin 사용)
    @Query(
            value="select pc.pcname, count(*) as count " +
            " from product p inner join productcategory pc " +
            " on p.pcno= pc.pcno " + //on절: pk-fk 교/합집합을 위한 조건절(vs where)
            " group by pc.pcname" // group by 필드명 : ~별 (그룹)

            ,nativeQuery = true)
    List <Map<Object,Object> > findByPieChart();

}


/*
    DTO,ENTITY 아닌 타입이 정해져 있지 않은 MAP<Object,Object> 사용
    - entry
    {key,value}
    필드명, 필드에 해당하는 값

    -MAP: 여러개 entry가 모이면 하나의 MAP (레코드1줄)
    {key,value},{key,value},{key,value}{key,value}
     필드명,값

    여러개 MAP저장하기 위한 LIST(여러개레코드)
    List =[MAP,MAP,MAP]

*/