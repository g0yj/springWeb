package example.day04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 메타 어노테이션 ? : 실행 또는 커파일 했을때 사용 방법에 대해 정의


 */
@SpringBootApplication      // 처음에 모든 컴포넌트들을 찾아서 빈에 등록 한다
public class AppStart {

    public static void main(String[] args) {

        SpringApplication.run(AppStart.class);
    }
}

/*
    스프링이 view 파일들을 찾는 위치 resources 폴더
        주의할점 : 본인이 만들고 싶은곳에 정적 파일 만들며 안된다
    html : resources->templates->html 파일
    js/css/image : resources-> static -> js/css/image 파일

    jsp 프로젝트와 spring 프로젝트의 정적파일 경로 차이
        jsp 는 패키지의 경로와 파일명이 곧 url

        spring 은 정적파일 호출하는 url 매핑 해야된다



 */