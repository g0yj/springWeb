package day01.consoleMvc;

import java.time.LocalDate;
import java.util.List;

public class ConsoleController {

    public List<ConsoleDto> doGet(){
        ConsoleDao consoleDao = new ConsoleDao();
        List<ConsoleDto> result = consoleDao.doGet();
        return result;

    }

    public boolean doPost(String title){
        ConsoleDto consoleDto = new ConsoleDto(0, title, LocalDate.now(),true);
        ConsoleDao consoleDao = new ConsoleDao();
        boolean result = consoleDao.doPost(consoleDto);
        return result;
    }


}//c
