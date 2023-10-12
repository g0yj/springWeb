package example.day02.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//jsp 프로젝트는 자동으로 서블릿 클래스 멤버 세팅해줬었음. (인텔리제이도 유료버전은 있)
    //WebServlet(name="서블릿이름[생략시클래스명으로자동]",urlPatterns = "서블릿연결할http경로") /
@WebServlet(name="myServlet",urlPatterns = "/day02/my") // 브라우저의 경로와 해당 서블릿을 연결하는 설정을 위해 사용

public class MyServlet extends HttpServlet {

    //서블릿 생성 방법
    //1. @@WebServlet
    //2. extends HttpServlet
            // 해당 HttpServlet 클래스로부터 다양한 rest메소드를 상속 받음
    //3. 오른쪽 클릭- 생성-메소드재정의[doget,dopost 등등등]


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        // resp.getWriter().println(); //아래코드랑 같음

        PrintWriter out = resp.getWriter();
        out.println("<html>");
            out.println("<body>");
                out.println("안녕 서브릿.");
             out.println("</body>");
        out.println("</html>");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }


}

/*
    p.28
        @: 어노테이션 :
            - 주석이나 해석. 주로 코드 상에 추가적인 정보를 남겨두기 위해 사용
            - 특정한 코드에 추가적인 처리나 설정을 위해 사용
            - 특정 클래스에 추가적인 외부 기능을 부여할 때 상속/구현체 보다 확장성이 좋음.(데이터전달까지가능)

         - 다른 클래스에게 데이터 전달하는 방법
            - extends : 상속 [메소드영역의 클래스 설계도 연장]
            - implement : 구현 [인터페이스의 추상메소드를 구현하는 구현체]
                -클래스 상속과 인터페이스 구현의 한계
                    1. 클래스는 상속 1번만 가능
                    2. 인터페이스는 메모리 전달 불가능. [일반 필드를 가질 수 없음. 무조건 상수]
             - @어노테이션
                1. 빌트인 : @Override(JDK 내 내장(기능)된 어노테이션)
                2. 메타데이터 : 외부라이브러리나 생성된 어노테이션 : 인터페이스나 클래스들을 확장해서 구조화된 데이터를 전달
                3. 사용자정의 : 개발자가 생성한 어노테이션
 */