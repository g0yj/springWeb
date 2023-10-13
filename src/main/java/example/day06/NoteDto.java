package example.day06;

import lombok.*;

import java.time.LocalDateTime;

/*
    Dto 사용처:Ajax(JSON/TEXT/FORM)---DTO--->Controller
        //JSON-
 */
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString
@Builder
public class NoteDto {
    private int no; //게시물번호
    private String title;
    private String writer;
    private String password;
    private LocalDateTime date;

    //DB에는 없지만 추가 해서 쓸 수 있는 [join]
    //*dto를 엔티티로 변환해주는 함수[service에서 사용]
    public NoteEntity toEntity(){
        return NoteEntity.builder()
                .date(this.date)
                .password(this.password)
                .no(this.no)
                .writer(this.writer)
                .title(this.title)
                .build();
    }

}
