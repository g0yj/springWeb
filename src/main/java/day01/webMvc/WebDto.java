package day01.webMvc;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@ToString@Builder
public class WebDto {
    private int tno;
    private  String title;
    private LocalDate dueDate;
    private boolean finished;

}//c
