package ezenweb.model.dto;

import ezenweb.model.entity.BaseTime;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.entity.ProductImgEntity;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@Builder@ToString
public class ProductDto{
    private String pno;
    private String pname;
    private String pcomment;
    private int pprice;
    private byte pstate;    //제품상태 [0:판매,1:판매중지,2:재고없음 3.폐기 등등]
    private int pstock;//재고

}
