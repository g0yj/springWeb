package ezenweb.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass//엔티티가X [여러 엔티티가 공통으로 사용하는 필드에 대해 구성할 때 ]
                                    // 공통된 어노테이션정보[여기서는 @CreatedDate,@LastModifiedDate] 등을 부모 클래스로 선언하고 어노테이션 정보를 자식클래스에게 제공
@EntityListeners(AuditingEntityListener.class)//AppStart에 @EnableJpaAuditing 없으면 소용 없음.
@Getter // MemberEntity.java 에서 빌드패턴 사용할때, cdate 만들때 쓰기위해 41,42번째줄
public class BaseTime {
    @CreatedDate//엔티티가 생성될 때 시간이 자동 저장/주입
    private LocalDateTime cdate; //생성날짜
    @LastModifiedDate//엔티티가 변경될 때 시간이 자동 저장/주입
    private LocalDateTime udate; //레코드/엔티티 수정날짜

    //날짜 형변환 메소드[LocalDateTime->String]
    public String toTimeOrDate(LocalDateTime dateTime) {
        return
        //[삼항연산자 사용]만약에 매개변수로 들어온 날짜가 현재 시간 날짜와 같으면
                dateTime.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString())?
                        dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")):
                        dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));



    }
}
/*
    BaseTime(이름상관없음!!): 주로 엔티티의 생성/수정 일시를 감지해서자동으로 업데이트 해주는 클래스. ()
    어노테이션
        1. @CreatedDate : 엔티티가 생성될 때 시간이 자동으로 저장
        2. @LastModifiedDate: 엔티티가 변경될 때 시간이 자동으로 저장
        3. @MappedSuperclass : JPA 엔티티 클래스들의 공통 필드를 상속할 때 사용하는 어노테이션[부모클래스]
        4. @EntityListeners(AuditingEntityListener.class) : 해당 클래스에서 엔티티 감사/감지
            - @EntityListeners : 엔티티에서 특정 이벤트가 발생 할때마다 특정 로직 실행
            - AuditingEntityListener.class: 감지 이벤트 실행
                -insert[@CreatedDate] 와 update[@LastModifiedDate]할때

            - @EnableJpaAuditing (같이 써줘야됨)
                - @SpringBootApplication 과 같은 위치에 선언 (AppStart.java)



 */