package example.day01.consoleMvc;

import java.time.LocalDate;

public class ConsoleDto {
    private int tno;
    private  String title;
    private LocalDate dueDate;
    private boolean finished;

    public ConsoleDto(){}; //직접입력


    public ConsoleDto(int tno, String title, LocalDate dueDate, boolean finished) {
        this.tno = tno;
        this.title = title;
        this.dueDate = dueDate;
        this.finished = finished;
    }

    public int getTno() {
        return tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ConsoleDto{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}'+"\n";
    }
}//c
