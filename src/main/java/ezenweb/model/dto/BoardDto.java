package ezenweb.model.dto;

import ezenweb.model.entity.BoardEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private String bfile;
    private int mno;

    private String memail;// 작성자아이디
    //스프링이 지원하는 인터페이스 사용[MultipartFile]
    private MultipartFile file;

    private String udate;  //엔티티 변화에 맞춰서 LocalDateTime 형식에서 String으로 형변환 했음!!
    private String cdate;


    //dto->entity
    //1. entity 저장할 때
    public BoardEntity saveToEntity(){
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bfile(this.bfile)
                .build();

    }


}
