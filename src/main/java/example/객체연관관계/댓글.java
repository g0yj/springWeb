package example.객체연관관계;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@ToString@Builder
public class 댓글 {
    private int 댓글번호;
    private String 댓글내용;

    private 게시물 댓글게시물;
}
