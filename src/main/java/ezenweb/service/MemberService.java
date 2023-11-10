package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService implements
                                        UserDetailsService, //일반회원서비스: loadUserByUsername 메소드 구현[로그인처리하는 메소드]
                                        OAuth2UserService<OAuth2UserRequest,OAuth2User>  // Oauth2 회원 서비스: loadUser메소드 구현
                                                                                        {
    //-------------------------------------------------------//
        //p.687
        //1. UserDetailsSercive 구현체
        //2. 시큐리티 인증 처리 해주는 메소드 구현[loadUserByUsername()]
        //3.loadUserByUsername() 메소드는 꼭 UserDetails객체를 반환해야됨!!
        //4. UserDetails 객체를 이용한 패스워드 검증과 사용자 권한을 확인하는 동작(메소드)


    //231030 11시
    /*
    @Autowired
    private PasswordEncoder passwordEncoder;
    오류: Field passwordEncoder in ezenweb.service.MemberService required a bean of type 'org.springframework.security.crypto.password.PasswordEncoder' that could not be found.
            The injection point has the following annotations:
	        - @org.springframework.beans.factory.annotation.Autowired(required=true)
    */


    //@Autowired : 사용 불가!! (스프링 컨테이너에 등록 안된 빈(객체)이므로 불가능 .. 따라서 new ()로 만들어줌!! )
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
       //---------------예시-----------------------------------------------------------------//
        /*
        System.out.println("username = " + username); // 입력받은 username 뜸. 그리고 오류남.
        return null;
        오류: UserDetailsService returned null, which is an interface contract violation
         */
        /*
        UserDetails userDetails = User.builder()
                .username("qwe") //아이디
                .password("1234") //패스워드  [암호화 없음]
                .authorities("ROLE_USER") //인가(허가나 권한) 정보
                .build();
        return userDetails;
        //오류: There is no PasswordEncoder mapped for the id "null" 패스워드 인코더 안해서 나는 오류.50번째줄 객체 이용
        */
        /*
        UserDetails userDetails = User.builder()
                .username("qwe") //아이디
                .password(passwordEncoder.encode("1234")) //[암호화 있음]
                .authorities("ROLE_USER") //인가(허가나 권한) 정보
                .build();
        return userDetails;
        */

        //----------------------예시끝--------------------------------------------------------//

        System.out.println("memail = " + memail);
        //p.648
        //1.사용자의 아이디만으로 사용자 정보를 로딩[불러오기] p.728
        MemberEntity memberEntity= memberRepository.findByMemail(memail);
            //없는 아이디면 //throw: 예외던지기  => 예외 클래스만들기!
                //throw new UsernameNotFoundException() :username 없을 때 사용하는 예외클래스
        if(memberEntity==null){
            throw new UsernameNotFoundException("없는 아이디임");
        }
        //2.로딩[불러오기]된 사용자의 정보를 이용해 패스워드 검증
            //있는 아이디이면

        //2-1 권한 목록 추가
        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        권한목록.add(new SimpleGrantedAuthority(memberEntity.getMrole()));
        //2-2 dto만들기
        MemberDto memberDto = MemberDto.builder()
                .memail(memberEntity.getMemail())
                .mname(memberEntity.getMname())
                .권한목록(권한목록)
                .build();

        /*
        UserDetails userDetails= User.builder()
                .username(memberEntity.getMemail()) //찾은 사용자 정보의 아이디
                .password(memberEntity.getMpassword()) //찾은 사용자의 정보의 패스워드
                .authorities(memberEntity.getMrole())   //찾은 사용자 정보의 권한
                .build();
       */
        return memberDto;

    }
    //-----------loadUserByUsername(String memail) 시큐리티 사용 [로그인] 함수 끝!!--------------------------------------------//

    //-----------Oauth2회원------------------------------------------------------------------------------------------------//
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //1. 로그인을 성공한 oauth2 사용자정보(동의항목)의 정보 호출
        OAuth2User oAuth2User=new DefaultOAuth2UserService().loadUser(userRequest);
        System.out.println("oAuth2User"+oAuth2User); //[{id=3AZ9Zg6cSAV5KyLJ8Gm1t8vATyBqSJmU3bQmp8OS9rA, nickname=jin, email=jinyjgo@naver.com}], Granted Authorities: [[ROLE_USER]], User Attributes: [{resultcode=00, message=success, response={id=3AZ9Zg6cSAV5KyLJ8Gm1t8vATyBqSJmU3bQmp8OS9rA, nickname=jin, email=jinyjgo@naver.com}}]

        //2. 인증결과(카카오,네이버,구글..)
        //2-1 인증한 소셜 서비스 아이디(각 회사명) 찾기
        String registrationId= userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId > "+registrationId); //google or nave or kakao
        //2-2 각 회사마다  registrationId 전달되는 형식이 다르기 때문에 조건으로 나눠야됨

        String memail =null; String mname=null; String mrole=null;

        // 카카오이면
        if(registrationId.equals("kakao")){
            /*
             oAuth2UserName:[3142818552],
             Granted Authorities: [
                                    [ROLE_USER, SCOPE_account_email, SCOPE_profile_nickname]
                                    ],
             User Attributes: [
                                {
                                id=3142818552,
                                connected_at=2023-11-01T03:18:37Z,
                                properties={nickname=고연진},
                                kakao_account={profile_nickname_needs_agreement=false,
                                profile={nickname=고연진},
                                has_email=true,
                                email_needs_agreement=false,
                                is_email_valid=true,
                                is_email_verified=true,
                                email=duswls3000@naver.com}
                                }
                               ]
             */
            //새로운 함수를 분석하기 위해서는 하나씩 찍어보는 방법 밖에 없음
            //System.out.println(oAuth2User.getAttribute("name").toString() );
                // 오류> 따라서 사용 못하는 거임.
            System.out.println(oAuth2User.getAttributes());
            //{id=3142818552, connected_at=2023-11-01T03:18:37Z, properties={nickname=고연진}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=고연진}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=duswls3000@naver.com}}
            System.out.println(oAuth2User.getAuthorities());
            //[ROLE_USER, SCOPE_account_email, SCOPE_profile_nickname]


            Map<String,Object> kakao_account=( Map<String,Object>)oAuth2User.getAttributes().get("kakao_account");
                //{profile_nickname_needs_agreement=false, profile={nickname=고연진}
            System.out.println("kakao_account>"+kakao_account);
            Map<String,Object> profile=( Map<String,Object>)kakao_account.get("profile");
            System.out.println("여기됨1");
            System.out.println("여기됨2");
            mname=profile.get("nickname").toString();
            memail=kakao_account.get("email").toString();

            Object[] authorities = oAuth2User.getAuthorities().toArray();

            System.out.println("memail> "+memail);
            System.out.println("mname> "+mname);
            System.out.println("authorities> "+authorities);



        }
        //네이버
        if(registrationId.equals("naver")){
            /*
             oAuth2UserName: [{id=3AZ9Zg6cSAV5KyLJ8Gm1t8vATyBqSJmU3bQmp8OS9rA, nickname=jin, email=jinyjgo@naver.com}],
             Granted Authorities: [[ROLE_USER]],
             User Attributes: [{resultcode=00, message=success, response={id=3AZ9Zg6cSAV5KyLJ8Gm1t8vATyBqSJmU3bQmp8OS9rA, nickname=jin, email=jinyjgo@naver.com}}]
             */
            Map<String,Object> response= ( Map<String,Object>)oAuth2User.getAttributes().get("response");
            System.out.println("response> "+response);
            mname=(String) response.get("nickname");
            memail=(String) response.get("email");
            System.out.println("mname> "+mname);
            System.out.println("memail> "+memail);


        }
        //구글이면
        if(registrationId.equals("google")){
            /*
            oAuth2UserName: [110538598241281790855],
            Granted Authorities: [[ROLE_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]],
            User Attributes: [{sub=110538598241281790855, name=고연진, given_name=연진, family_name=고, picture=https://lh3.googleusercontent.com/a/ACg8ocIrfBSQlsscqFE1kDE7rlXapy5vlmDwXE6ruGWR7oRT=s96-c, email=jinyjgo@gmail.com, email_verified=true, locale=ko}]
             */
        }

        //3. 일반회원(UserDetails)+Oauth2 통합 회원 만들기 = DTO를 같이 쓰겠다!
            //MemberDto 가서 implements 해주기
            //2-1 훤한 목록 추가
        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        권한목록.add(new SimpleGrantedAuthority("ROLE_"+registrationId));
            //2-2 dto 만들기
        MemberDto memberDto = MemberDto.builder()
                //oauth2는 패스워드를 알 수 없음 (각 회사가 관리하는거임)
                .memail(memail)
                .mname(mname)
                .권한목록(권한목록)
                .소셜회원정보(oAuth2User.getAttributes())
                .build();
            //2-3 db처리

                //만약에 처음 적속이면 oauth2 회원이면 db처리
            if(!memberEntityRepository.existsByMemail(memail)){ //해당 이메일이 db에 없으면
               memberDto.setMrole("ROLE_USER");
               //임의 패스워드[oauth2 패스워드가 없음. entity에 비번에 nullable=false 해놨음!! ,db에 null을 피하기 위해 임의의 수를 넣어줌]
               memberDto.setMpassword(new BCryptPasswordEncoder().encode(mname));
               //임의 전화번호[카카오전화번호 가져오려면 사업자 번호 있어야한다 햇음. .!]
               memberDto.setMphone("010-1111-1111"); //추후 수정페이지로 이동시켜줌으로써 추가 정보 입력하게 유도
               memberEntityRepository.save(memberDto.toEntity());
            } else {//처음 접속이 아니면 기존 권한을 db에서 가져와서 넣어주기
                memberDto.setMrole(memberEntityRepository.findByMemail(memail).getMrole());
            }
            //권한추가
            memberDto.get권한목록().add(new SimpleGrantedAuthority(memberDto.getMrole()));

            return memberDto;
    }


    @Autowired
    private MemberEntityRepository memberRepository;

    //1. 회원가입
    @Transactional//트랜잭션: 여러개 sql을 하나의 최소 단위로[성공/실패!! 함수 내 일부 sql만 성공은 불가능]
    public boolean postMember(MemberDto memberDto) {
        //------------------------암호화---------------------------------------------------//
            //입력받은 비밀번호[(memberDto.getMpassword())]를 암호화 해서 다시 memberDto에 저장
                //오류: Data truncation: Data too long for column 'mpassword' at row 1
                    //이유: entity에 만들었던 비밀번호 필드의 최대길이가 암호화된 길이보다 짧음..
                    //해결: entity 필드의 최대길이를 늘려줌.
        memberDto.setMpassword(passwordEncoder.encode((memberDto.getMpassword())));

        //-----------------------암호화------------------------------------------------------//

        System.out.println("memberDto = " + memberDto);
        //1. dto->entity 변경 후 repository를 통한 insert  후 결과 entity받기
        MemberEntity memberEntity = memberRepository.save(memberDto.toEntity());

        //2. insert 된 엔티티 확인 후 성공/ 실패 유무
        if (memberEntity.getMno() >= 1) {//만약 회원번호가 0보다 크면 (auto_increqment 적용됨)
            return true;
        }
        return false;
    }

    /*
        //2. 회원정보호출 [세션을 구현 하지 않았을때]
        @Transactional
        public MemberDto getMember(@RequestParam int mno){
            System.out.println("mno = " + mno);

            //1. mno를 이용한 엔티티 찾기 ->.findById(mno) 반환값은 Optinal<>임!!!!!
            Optional<MemberEntity> optionalMemberEntity=memberRepository.findById(mno);

            //2.optinal클래스로 검색한 반환 값 확인
           if(optionalMemberEntity.isPresent()){//3. 만약에 optinal 클래스 안에 엔티티가 들어가 있으면,
               // .isPresent(): Optional객체를 사용하기전에 null 인지 아닌지 체크하고 사용

               //4. optinal 클래스에서 엔티티 꺼내기
               MemberEntity memberEntity=optionalMemberEntity.get();

               //5.entity->dto 변환해서 반환
               return memberEntity.toDto();

           }
            return null;
        }
    */

    @Autowired
    public MemberEntityRepository memberEntityRepository;
    //2.회원 정보 호출 (header.js[axios] , boardService[write] 에서 쓰임.)
    @Transactional
    @GetMapping
    public MemberDto getMember() {
        /* --기존 세션을 활용한 호출(시큐리티 사용 전에는 서블릿 세션을 이용한 로그인상태 저장)--------
        System.out.println("MemberService.getMember");
        //1.
        Object session = request.getSession().getAttribute("loginDto");
        //2.
        if (session != null) {
            return (MemberDto) session;
        }
        return null;

        --------------------------------------------------------------------- */
        //시큐리티 사용할때는 일단 서블릿 세션이 아니고 시큐리티 저장소를 이용함----------------------------------------------------------
        //System.out.println("시큐리티에 저장된 세션 정보 저장소: "+SecurityContextHolder.getContext());
        //System.out.println("시큐리티에 저장된 세션 정보 저장소에 저장된 인증: "+SecurityContextHolder.getContext().getAuthentication());
       // System.out.println("시큐리티에 저장된 세션 정보 저장소에 저장된 인증 호출(본인): "+SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //해당 서비스를 호출한 HTTP

        //인증에 성공한 정보 호출[=세션호출]
        Object o =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println(o.toString());
        //1.만약에 인증 결과가 실패이면/ 없으면
        if(o.equals("anonymousUser")){ //로그인 실패하면 console에 anonymousUser찍힘
            return null; //로그인 안했..
        }
        //2. 인증결과에 저장된 UserDetails로 타입변환
        UserDetails userDetails =(UserDetails)o; //UserDetails: 로그인 결과를 가지고 있는 객체
            //로그인상태에 필요한 데이터 구성(BoardService write() 함수에 사용해야도미)
            MemberEntity memberEntity =memberEntityRepository.findByMemail(userDetails.getUsername());
            return MemberDto.builder()
                    .memail(memberEntity.getMemail())
                    .mno(memberEntity.getMno())
                    .build();

    }



    //3. 회원정보 수정
    @Transactional//수정은 꼭 써줘야됨!!(여기 기준으로는 4개의 sql을 하나로 묶어줌)
    public boolean updateMember( MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        //1. 수정할 엔티티 찾기[mno]
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findById(memberDto.getMno());

        //2. optinal 클래스로 검색한 반환값 확인
        if(optionalMemberEntity.isPresent()){
            //3. 엔티티 꺼내기
            MemberEntity memberEntity1=optionalMemberEntity.get();
            //4. 엔티티 수정[엔티티 객체를 수정하면 엔티티는 테이블과 매핑된 상태이므로 db의 정보도 같이 수정]
            memberEntity1.setMname(memberDto.getMname());
            memberEntity1.setMpassword(memberDto.getMpassword());
            memberEntity1.setMphone(memberDto.getMphone());
            //5. 성공 시
            return true;
        }
        return false;
    }

    //4. 회원삭제
    @Transactional
    public boolean deleteMember(int mno){
        System.out.println("mno = " + mno);
        //1. 삭제할 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findById(mno);
        //2. 만약에 삭제할 엔티티가 반환/존재하면
        if(optionalMemberEntity.isPresent()){
            memberRepository.deleteById(mno);
            //회원삭제 성공 시 세션 날려;야됨. 이미 로그아웃함수에 포함되어있으니 함수 호출 방식으로 고고!
            //logout(); //시큐리티 쓰면서 필요없어짐
            return true;
        }

        return false;

    }


/*
    //5. 로그인
    @Autowired
    private HttpServletRequest request; //request객체도 스프링 컨테이너에 등록된 상태이기 때문에 그냥 꺼내오면 됨.
            public boolean login(MemberDto memberDto) {
        //1.입력받은 데이터[아이디,비번] 검증
        List<MemberEntity> memberEntities =memberRepository.findAll();


            //2. 동일한 아이디 비밀번호 찾기
            for(int i=0;i<memberEntities.size();i++) {
                MemberEntity m = memberEntities.get(i);
                //3.동일한 엔티티를 찾았다.
                if (m.getMemail().equals(memberDto.getMemail()) && m.getMpassword().equals(memberDto.getMpassword())) {
                    //4. 세션부여 //세션저장
                    request.getSession().setAttribute("loginDto", m.toDto());
                    return true;
                }
            }
        return false;
    }

    //6. [get]로그아웃

    public boolean logout() {
        System.out.println("MemberService.logout");
        request.getSession().setAttribute("loginDto",null);
        return true;
    }

*/

    //7. 아이디찾기 - 이름/전화번호
    public String findId(String mname,String mphone){
        System.out.println("MemberService.findId");
            List<MemberEntity> memberEntities=memberRepository.findAll();
            for(MemberEntity e:memberEntities){
                if(e.getMname().equals(mname)&&e.getMphone().equals(mphone)){
                    return e.getMemail();
                }
            }
        return null;
    }

    //8. 비밀번호찾기 - 이메일/전화번호
    public String findPw(String memail,String mphone){
        System.out.println("MemberController.findPw");
        List<MemberEntity> memberEntities = memberRepository.findAll();
        for(MemberEntity e:memberEntities){
            if(e.getMemail().equals(memail)&&e.getMphone().equals(mphone)){
                return e.getMpassword();
            }
        }
        return null;
    }

    //9. 이메일 중복검사
    public boolean getFindEmail(String memail){
       System.out.println("getFindEmail 서비스 도착");
       System.out.println("memail = " + memail);

       boolean result =memberRepository.existsByMemail(memail);
                                        //findByMemail(): 리파지토리에서 재정의해서 만들어냄!!
                                             //동일한 이메일 있을 때 'true'없을 때 false 반환
        System.out.println("서비스결과> "+result);
      return result;
    }




}
