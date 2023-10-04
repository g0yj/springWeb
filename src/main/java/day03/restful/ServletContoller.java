package day03.restful;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//스프링에서는 지원해주기 때문에 별도로 쓰지 않음

@WebServlet("/day03")
public class ServletContoller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://localhost:8080/day03?param1=안녕
        //1. 요청
        String param1 = req.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("정상응답");

    }//f()

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://localhost:8080/day03?param1=안녕
        //1. 요청
        String param1 = req.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("정상응답");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://localhost:8080/day03?param1=안녕
        //1. 요청
        String param1 = req.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("정상응답");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://localhost:8080/day03?param1=안녕
        //1. 요청
        String param1 = req.getParameter("param1");
        System.out.println("param1 = " + param1);
        //2. 응답
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("정상응답");
    }
}//c
