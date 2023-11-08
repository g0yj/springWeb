package ezenweb.model.entity;

import ezenweb.model.dto.BoardDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "board")
@DynamicInsert//@ColumnDefault가 적용될 수 있도록 해주는 어노테이션
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @Builder
public class BoardEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//mysql 기준으로 auto_increment 기능.
    private int bno;
    @Column(name="btitle",length = 200, nullable = false)
        //생략 가능하나 필드 속성을 커스텀 하기 위해서는 작성해야됨.
    private String btitle;
    @Column(columnDefinition = "longtext" ,nullable = true) //DB에서는 longtext를 쓰는데 JAVA에는 longtext 없음. 이때 columnDefinition 사용
    private String bcontent;
    @Column
    @ColumnDefault("0")//0은 숫자니까 '' 안 넣어도됨.
    private int bview;
    // private LocalDateTime bdate; <----BaseTime 클래스로부터 상속 받을거기 때문에 하지 X
                                        // 상속 해주는 필드: 작성일, 수정일
    @Column(columnDefinition = "longtext" ,nullable = true)
    private String bfile;


    @ToString.Exclude//ToString 함수를 제외하는 필드
    @JoinColumn(name="mno")//FK 필드로 사용 (name="fk필드명")
    @ManyToOne//다수가 하나에게 FK
                //실제 DB에는 엔티티의 ID(pk)로 저장됨.
    private MemberEntity memberEntity;


    //entity->dto로 변환하는 함수[상황에 따라 여러개 선언]

    //1. 전체 출력
    public BoardDto allToDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bfile(this.bfile)
                .bview(this.bview)
                .mno(this.memberEntity.getMno())
                .cdate(toTimeOrDate(this.getCdate())) //basetime에서 만든 함수 확인!
                .udate(toTimeOrDate(this.getUdate()))
                .memail(this.memberEntity.getMemail().split("@")[0]) //작성자 아이디[fk연결된거임. 양방향~!]
                .build();
    }

}

/*
    직접 DDL 작성해서 테이블 생성
    create table board(
        bno int auto_increment,
        btitle varchar(100),
        bcontent longtext,
        bview int,
        bdate datetime default now(),
        udate datetime default now(),
        bfile longtext,
        mno int,
        primary key (bno)

    );
 */