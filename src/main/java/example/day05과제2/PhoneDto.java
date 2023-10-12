package example.day05과제2;

import lombok.*;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString
@Builder
public class PhoneDto {
    private String name;
    private String number;
    private int pno;
}
