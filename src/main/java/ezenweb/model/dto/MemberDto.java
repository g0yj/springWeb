package ezenweb.model.dto;

import ezenweb.model.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@Builder@ToString
public class MemberDto {

    private int mno;
    private String memail;   //회원 아이디 대체
    private String mpassword;
    private String mname;
    private String mphone;
    private String mrole;   //회원등급(일반,관리자)

    //+baseTime 할거라면,
    private LocalDateTime cdate;
    private LocalDateTime udate;
    //dto-> entity 변환 함수(dto-> entity)
        //service에서 dto정보를 db테이블 매핑에 저장하기
    public MemberEntity toEntity(){
        return MemberEntity.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .build();
    }
}
