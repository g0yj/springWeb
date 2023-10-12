package example.day05과제2;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity//JPA (MYSQL 테이블과 매핑)
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEntity {
    @Id  //import javax.persistence. , PK로 선정할 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int pno;
    private String name;
    private String number;

}
