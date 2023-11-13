package ezenweb.model.dto;

import ezenweb.model.entity.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor@NoArgsConstructor
@Setter@Getter@Builder@ToString
public class MemberDto implements UserDetails , OAuth2User {

    //-----------UserDetails----------------------//
    //Collection 프레임워크 : set,list,map
    List<GrantedAuthority> 권한목록;

    @Override//계정권한 목록[Collection으로 반환= 권한 여러개가 가능함!]
    public Collection<? extends GrantedAuthority> getAuthorities() {return 권한목록;}
    @Override
    public String getPassword() {return mpassword;}
    @Override
    public String getUsername() {return memail;}
    @Override //계정 만료 여부 [true:열림]
    public boolean isAccountNonExpired() {return true;}
    @Override// 계정 잠금 여부
    public boolean isAccountNonLocked() {return true;}
    @Override // 계정 증명 여부
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return true;}

    //-----------UserDetails오버라이드-----------------------///

    //-------OAuth2User오버라이드---------------------------//
    private Map<String,Object> 소셜회원정보;

    @Override //.인증성공한 회원 정보
    public Map<String, Object> getAttributes() {return null;}
    @Override // 회원아이디
    public String getName() {return memail;}

    //-------OAuth2User오버라이드---------------------------//


    private int mno;
    private String memail;   //회원 아이디 대체
    private String mpassword;
    private String mname;
    private String mphone;
    private String mrole;   //회원등급(일반,관리자)

    //+baseTime 할거라면,
    private LocalDateTime cdate;
    private LocalDateTime udate;
    //dto-> entity 변환 함수(dto-> entity)
        //service에서 dto정보를 db테이블 매핑에 저장하기
    public MemberEntity toEntity(){
        return MemberEntity.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .build();
    }
}
