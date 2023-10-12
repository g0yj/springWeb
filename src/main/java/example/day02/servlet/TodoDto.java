package example.day02.servlet;

import lombok.*;

import java.time.LocalDate;
//롬복 라이브러리 사용
@Getter@Setter
@ToString
@NoArgsConstructor //빈 생성자
@AllArgsConstructor//풀생성자
@Builder//빌더패턴: 객체 생성 시 사용 할 수 있는 함수 [생성자보다 확장성 가짐]
public class TodoDto {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
