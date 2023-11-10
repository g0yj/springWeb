package ezenweb.model.entity;

import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
//제품이미지파일저장
@Entity@Table(name="productimg")
public class ProductImgEntity extends BaseTime{
    @Id
    private String uuidFileName; //이미지식별이름. uuid파일이 식별키가 되니까 PK
    @Column
    private String realFileName; //이미지 실제이름

    //단방향 fk 설정
    @JoinColumn(name="pno")@ManyToOne
    private ProductEntity productEntity;


}
