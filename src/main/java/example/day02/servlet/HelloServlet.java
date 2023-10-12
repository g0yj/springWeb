package example.day02.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet" , value ="/hello-servlet")
public class HelloServlet extends HttpServlet {
    //오른쪽 클릭 > 생성 > 오버라이딩 메소드
    // -- 서블릿의 라이프 사이클 [생명주기: 서블릿객체가 생성 되는 실행되고 제거 되기 까지]

    private  String message;
    
    //1. 최초로 해당 서블릿의 url 요청했을 때 1번 실행됨 [ 다음 요청부터는 동일한 서블릿 객체를 사용하므로 더이상 실행하지 않음]
    @Override
    public void init() throws ServletException {
        System.out.println("HelloServlet.init");
        message="서블릿 객체 생성";

    }
    //2. 해당 서블릿의 url 요청 햇을 때 get,post,put,delete 등 메소드 매핑 연결[요청마다]
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("HelloServlet.service"); //soutm
        super.service(req,res);
    }
    
    //3. 서비스가 호출한 메소드들 get,post,put,delete 등등
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.doGet");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println(message);
    }


    //4. 톰캣[서버]가 종료될 때 [서블릿의 객체 사라짐] 실행
    @Override
    public void destroy() {
        System.out.println("HelloServlet.destroy");//soutm

    }


}
