package ezenweb.model.dto;

import ezenweb.model.entity.BaseTime;
import ezenweb.model.entity.ProductEntity;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder@ToString
public class ProductCategoryDto{
    private int pcno;
    private String pcname;


}
