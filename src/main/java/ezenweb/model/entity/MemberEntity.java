package ezenweb.model.entity;

import ezenweb.model.dto.MemberDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity//해당 클래스를 DB 테이블과 매핑 [엔티티 클래스가 곧 테이블!! 엔티티 객체가 테이블 내 레코드 한개. 엔티티클래스는 db테이블 한개 ]
@Table(name="member")//db테이블명 정의 [생략 시 해당 클래스명이 곧 db테이블명으로 자동생성]
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @Builder @ToString
public class MemberEntity extends BaseTime{
                            //상속 주면 테이블에 상속 받은거 추가됨.(udate,ndate)
   @Id //오류 해결: 해당 필드는 Id가 필수
   @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int mno;
    @Column(name="memail", length = 50,nullable=false, unique = true)    //해당 필드선정.
            // [속성=>>> name="필드명" , nullable=false(=null불가), unique=true(=중복불가),length(=글자수) ]
    private String memail;   //회원 아이디 대체
    @Column(length = 30,nullable = false)
    private String mpassword;
    @Column(length = 20,nullable = false)
    private String mname;
    @Column(length = 13,nullable = false,unique = true)
    private String mphone;
    @ColumnDefault("'user'")// @ColumnDefault("기본값") 지정. 문자이기 때문에 ''를 넣어줘야함.
    private String mrole;   //회원등급(일반,관리자)


   //entitiy-> dto 변환 함수(dto->entity)
      //service에서 entity 정보를 controller 이동하기 위해.
  public MemberDto toDto(){
    return MemberDto.builder()
          .mno(this.mno)
          .memail(this.memail)
          .mpassword(this.mpassword)
          .mname(this.mname)
          .mphone(this.mphone)
          .mrole(this.mrole)
          .cdate(this.getCdate())
          .udate(this.getUdate())
          .build();
  }
}


//실행
    //오류 : org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: No identifier specified for entity: ezenweb.model.entity.MemberEntity
        //No identifier specified for entity: ezenweb.model.entity.MemberEntity -> @Id값 반드시 가져야함.