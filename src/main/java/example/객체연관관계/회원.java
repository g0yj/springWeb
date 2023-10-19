package example.객체연관관계;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
@ToString //객체 호출 시 기본값은 객체 주소가 호출되는데, ToString을 사용 시에는 참조값이 아닌 객체 참조값의 필드가 호출됨.
            //순환관계에서 toString을 쓰지 않으면 문제 되지 않음. 사용하려면  @ToString.Exclude를 사용해 제한을 걸어줌.
                                                                //연관되는 두 클래스 중에 하나만 써주면 되는데 ...주로 참조객체에 @ToString을 제외하는 걸 권장함. 따라서 여기는 게시물객체에 넣어줬음.
public class 회원 {
    private int 회원번호;
    private String 회원아이디;
    private String 회원이름;
    //리스트: 1명의 객체는 여러개의 게시물을 가질 수 있으니까.
    @Builder.Default//빌더패턴 사용 시 해당 필드는 기본값으로 사용.
        //사용하는 이유 ??? 참고
                        // https://quantum-jumpin.tistory.com/5#:~:text=%EC%83%9D%EC%84%B1%EC%9E%90%EB%A5%BC%20%ED%86%B5%ED%95%B4%20%EC%83%9D%EC%84%B1%ED%95%9C,%40Builder.%20Default%EB%A5%BC%20%EB%B6%99%EC%9D%B8%EB%8B%A4.
    private List<게시물> 내가쓴글= new ArrayList<>();
}
