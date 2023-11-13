package ezenweb.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity@Table(name = "productcategory")
public class ProductCategoryEntity extends BaseTime {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcno;
    @Column
    private String pcname;
    //양방향만들기
    @OneToMany(fetch =FetchType.LAZY ,  mappedBy ="productCategoryEntity",cascade = CascadeType.ALL)
    @ToString.Exclude //순환방지 PK쪽에 거는 걸 추천 (FK쪽 걸어도 ㄱㅊ긴함)
    private List <ProductEntity> productEntityList= new ArrayList<>();




}
/*
    fetch: 양방향일 때 참조를 불러오는 로딩 옵션 (DB랑 연관 해서 생각하지 X  엔티티에서 생각함)
        fetch =FetchType.LAZY  : 참조를 사용할 때 로딩[지연 로딩]. 양방향 쓰지 않을 수 있으니 권장함.
                                                        ㄴ 자바에서 .get~ 할때 객체 참조해서 불러오고
        fetch =FetchType.EAGER[기본값] : 참조값을 즉시 로딩[즉시 로딩] 규모 크면 느려지겄지 ...
                                                      ㄴ (리파지토리가)db에서 select 할때 객체 참조해서 불러오고


    cascade: 영속성 제약조건: 서로 연관된 객체들끼리 (부모-자식)의 영향을 끼치게 할껀지 (DB랑 연관 해서 생각하지 X  엔티티에서 생각함)
        cascade = CascadeType.ALL : REMOVE와 PERSIST 둘 다 적용
        cascade = CascadeType.REMOVE : 부모(엔티티객체)가 삭제될 때 자식도 같이 삭제[부모와 자식 엔티티를 모두 제거]
        cascade = CascadeType.PERSIST: 부모(엔티티객체) 호출 할 때 자식도 하나로 인식[부모와 지식 엔티티를 한번에 영속화]
                    - 부모를 저장하면 자식도 같이 저장 (영속성 없으면 부모,자식 각각 저장해야됨)
        cascade = CascadeType.REFRESH: 부모(엔티티객체)가 업데이트되면 자식객체 값도 새로고침.
        cascade = CascadeType.DETACH : 영속성 제거
        cascade = CascadeType.MERGE : 부모(엔티티객체) 수정될 때 자식객체도 조회 후 업데이트

       이미 db랑 연결되어 있기 때문에 엔티티 바뀌면 db도 바뀌긴함. 개념 이해에 헷갈리니까 db랑 연관짓지 마셈!

 */