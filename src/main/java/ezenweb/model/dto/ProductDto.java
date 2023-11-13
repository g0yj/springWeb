package ezenweb.model.dto;

import ezenweb.model.entity.BaseTime;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.entity.ProductImgEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

//등록용======================================================
    //첨부파일이 여러개일때[첨부파일이 하나일때 =boardDto]
    private List<MultipartFile> fileList; //js에서 multiple 썻음. <input tyle="file" name="fileList multiple/>
    //+ 카테고리넘버
    private int pcno;

//출력용========================================================
    private ProductCategoryDto categoryDto;
    private List<ProductImgDto> imgList;
}
