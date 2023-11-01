package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements
                                        UserDetailsService, //일반회원서비스: loadUserByUsername 메소드 구현[로그인처리하는 메소드]
                                        OAuth2UserService<OAuth2UserRequest,OAuth2User> {
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
        //오류: There is no PasswordEncoder mapped for the id "null"
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
        UserDetails userDetails= User.builder()
                .username(memberEntity.getMemail()) //찾은 사용자 정보의 아이디
                .password(memberEntity.getMpassword()) //찾은 사용자의 정보의 패스워드
                .authorities(memberEntity.getMrole())   //찾은 사용자 정보의 권한
                .build();
        return userDetails;

    }
    //-----------loadUserByUsername(String memail) 시큐리티 사용 [로그인] 함수 끝!!--------------------------------------------//


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //1. 로그인을 성공한 oauth2 계정의 정보 호출
        OAuth2User oAuth2User=new DefaultOAuth2UserService().loadUser(userRequest);
        System.out.println("oAuth2User"+oAuth2User);
        return null;
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
        System.out.println("시큐리티에 저장된 세션 정보 저장소: "+SecurityContextHolder.getContext());
        System.out.println("시큐리티에 저장된 세션 정보 저장소에 저장된 인증: "+SecurityContextHolder.getContext().getAuthentication());
        System.out.println("시큐리티에 저장된 세션 정보 저장소에 저장된 인증 호출(본인): "+SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //해당 서비스를 호출한 HTTP

        //인증에 성공한 정보 호출[=세션호출]
        Object o =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(o.toString());
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
