package ezenweb.model.dto;

import ezenweb.model.entity.BaseTime;
import ezenweb.model.entity.ProductEntity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ProductImgDto {
    private String uuidFileName; //이미지식별이름. uuid파일이 식별키가 되니까 PK
    private String realFileName; //이미지 실제이름

}
