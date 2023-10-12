package example.day05;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class TodoDto {
    private int tno;
    private String tcontent;
    private boolean tstate;
}
