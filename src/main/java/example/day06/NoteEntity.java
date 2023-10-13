package example.day06;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
    DDL : 테이블 생성, 테이블 수정, 테이블 삭제
        JSP 프로젝트 -> MYSQL 워크벤치 작성
        SPRING DATA JPA -> SQL 작성을 할 필요가 없다. 엔티티클래스 곧 테이블 생성

 */

@Entity //해당 클래스가 엔티티임을 주입.[실제 테이블 매핑/연결]
@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@ToString@Builder
public class NoteEntity {
    @Id//no필드를 pk필드 선정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private int no; //게시물번호
    private String title;
    private String writer;
    private String password;
    private LocalDateTime date;

    //*엔티티를 dto로 변환해주는 함수
    public NoteDto toDto(){
        return new NoteDto(this.no,
                        this.title,
                        this.writer,
                        this.password,
                        this.date
                        );
    }
}
