package example.day04;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Spring mvc 는 아니지만 Spring Bin에 등록
@Component //-> 이걸 사용하면 빈에 등록되어 다른데서 호출할 수 있다
public class TodoDao {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    //비어있는 생성자에 db 연동

    public TodoDao(){
        try { // 서버 연동
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springweb","root","1234");
            System.out.println("ConsoleDao 연동성공");

        }catch (Exception e){
            System.out.println("ConsoleDao 연동실패 " + e);
        }

    }

    // 1.
    public boolean doPost(TodoDto todoDto){
        String sql = "insert into todo(tcontent , tstate) values(?,?)";

        try {
        ps=conn.prepareStatement(sql);
        ps.setString(1,todoDto.getTcontent());
        ps.setBoolean(2,todoDto.isTstate());
        int count = ps.executeUpdate();
        if(count ==1 ){return true;}

        }catch (Exception e){
            System.out.println("포스트 에러 + " + e);
        }return false;

    }

    // 2.
    public List<TodoDto> doGet(){

        List<TodoDto> list = new ArrayList<>();
            String sql = "select * from todo";
        try {
            ps=conn.prepareStatement(sql);
            rs= ps.executeQuery();

            while (rs.next()){
                list.add(TodoDto.builder().tno(rs.getInt("tno"))
                        .tcontent(rs.getString("tcontent"))
                        .tstate(rs.getBoolean("tstate")).build());
            }

        }catch (Exception e ){System.out.println("겟 에러 " + e); }
        return list;
    }


    // 3.
    public boolean doPut(TodoDto todoDto){

        String sql = "update todo set tstate = ? where tno = ?";
        try {
        ps = conn.prepareStatement(sql);
        ps.setBoolean(1,todoDto.isTstate());
        ps.setInt(2,todoDto.getTno());
            int count = ps.executeUpdate();
            if(count == 1){return  true;}
        }catch (Exception e){System.out.println("풋 에러 " + e);}
        return false;
    }

    // 4.
    public  boolean doDelete(int tno){
        String sql = "delete from todo where tno = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,tno);
            int count = ps.executeUpdate();
            if(count==1){return true;}

        }catch (Exception e){System.out.println("딜리트 에러 " + e);}
        return false;
    }

}
