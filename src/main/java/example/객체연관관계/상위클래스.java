package example.객체연관관계;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class 상위클래스 {
    private String value;
    @Builder.Default
    private List<하위클래스> 참조하위객체들 = new ArrayList<>();

}
