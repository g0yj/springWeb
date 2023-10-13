package example.day06;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String cid;
    private String ctype;
    private LocalDateTime cdate;

    public CarDto toDto(){
        return new CarDto(
                        this.cno,
                        this.cid,
                        this.ctype,
                        this.cdate

        );

    }

}
