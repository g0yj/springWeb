package example.객체연관관계;
/*
 게시물,댓글,회원
*/
public class Main {
    public static void main(String[] args) {
        System.out.println("메인함수실행");

        //--------------------단(한쪽에서만 참조 중) 방향 관계------------------------------//

        //1. [A객체생성]'강호동' 회원가입 진행
        회원 A객체 = 회원.builder()
                .회원번호(1)
                .회원아이디("qwe")
                .회원이름("강호동")
                .build();
        //2.강호동 회원객체가 글쓰기를 진행
                    /*
                    게시물 B객체 = 게시물.builder()
                            .게시물번호(1)
                            .게시물제목("강호동이쓴글")
                            .게시물내용("강호동이 작성한 내용")
                            .build();

                 B객체와 A객체와의 관계?! 아직까진 없음!!
                 A객체가 B객체를 생성됐다는 증거가 없죵 . DB에서야 PK-FK가 있지만 자바는 없음
                    */

        게시물 B객체 = 게시물.builder()
                .게시물번호(1)
                .게시물제목("강호동이쓴글")
                .게시물내용("강호동이 작성한 내용")
                .작성한회원(A객체) //B게시물을 작성한 회원A객체
                .build();
        //3. B객체(A객체참조)
        System.out.println("B객체 = "+B객체);

        //---------------------------------------------------//
        //4.'게시물 댓글달기'(C객체) 댓글 작성 진행[비회원제]
        댓글 C객체 = 댓글.builder()
                .댓글번호(1)
                .댓글내용("댓글 내용입니다")
                .댓글게시물(B객체) //B객체(게시물)에 C객체(댓글)달기
                .build();
        System.out.println("C객체 = "+C객체);

     //-------------------양방향관계------------------------------------------//
    A객체.get내가쓴글().add(B객체);

    게시물 D객체 = 게시물.builder()
            .게시물번호(2)
            .게시물제목("강호동이쓴글2")
            .게시물내용("강호동이 작성한 내용2")
            .작성한회원(A객체) //D게시물을 작성한 회원A객체
            .build();


    }
}
