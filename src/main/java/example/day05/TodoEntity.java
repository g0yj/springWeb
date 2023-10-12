package example.day05;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //JPA(ORM매핑) MYSQL 테이블과 매핑
@Builder
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoEntity {
    @Id//PK로 선정할 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private int tno;
    private String tcontent;
    private boolean tstate;

}