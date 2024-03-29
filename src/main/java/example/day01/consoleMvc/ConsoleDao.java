package example.day01.consoleMvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsoleDao {

    public Connection conn;
    public PreparedStatement ps;
    public ResultSet rs;

    public ConsoleDao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/springweb","root","1234");
            System.out.println("DB연결성공");
        }catch (Exception e){System.out.println("연동실패"+e);}

    }
    public List<ConsoleDto> doGet(){
        List<ConsoleDto> list = new ArrayList<>();
        try {
            String sql ="select*from doto";
            ps=conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                ConsoleDto consoleDto = new ConsoleDto(
                        rs.getInt(1),
                        rs.getString(2),
                        LocalDate.parse(rs.getString(3)), //dto랑 sql의 타입이 맞지 않아서 변환해준거임
                        rs.getBoolean(4));
                list.add(consoleDto);
            }//w
        }catch (Exception e){
            System.out.println("DAO오류> "+e);
        }
        return list;
    }
    public boolean doPost(ConsoleDto dto){
        try {
            String sql ="insert into doto(title,dueDate,finished) values (?,?,?)";
            ps =conn.prepareStatement(sql);
            ps.setString(1,dto.getTitle());
            ps.setString(2,dto.getDueDate().toString());
            ps.setBoolean(3,dto.isFinished());
            int count=ps.executeUpdate();
            if(count==1){return true;}
        }catch (Exception e){
            System.out.println("DAO오류> "+e);
        }

        return false;
    }


}
