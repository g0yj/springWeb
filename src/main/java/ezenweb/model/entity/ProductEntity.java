package ezenweb.model.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
//제품테이블
@Entity@Table(name="product")
public class ProductEntity extends BaseTime{
    @Id
    private String pno;
    @Column
    private String pname;
    @Column(columnDefinition = "TEXT")
    private String pcomment;
    @Column
    private int pprice;
    @Column@ColumnDefault("0")
    private byte pstate;    //제품상태 [0:판매,1:판매중지,2:재고없음 3.폐기 등등]
    @Column@ColumnDefault("0")
    private int pstock;//재고

    //단반향 : fk만들기 ,Entity가 이미 id를 가지고 있어서 연결 가능함.
    @JoinColumn(name="pcno")
    @ManyToOne// 해당 타입의 id값 찾아 매핑시켜줌
    private ProductCategoryEntity productCategoryEntity;

    //양방향만들기
    @OneToMany(fetch =FetchType.LAZY, mappedBy = "productEntity", cascade = CascadeType.ALL)
    @ToString.Exclude//순환방지 PK쪽에 거는 걸 추천 (FK쪽 걸어도 양쪽 다 걸어도 ㄱㅊ긴함)
    private List<ProductImgEntity> productImgEntityList  = new ArrayList<>();
}
