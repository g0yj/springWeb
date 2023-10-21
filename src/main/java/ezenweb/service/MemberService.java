package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberEntityRepository memberRepository;

    //1. 회원가입
    @Transactional//트랜잭션: 여러개 sql을 하나의 최소 단위로[성공/실패!! 함수 내 일부 sql만 성공은 불가능]
    public boolean postMember(MemberDto memberDto) {
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

    //2.회원 정보 호출
    @GetMapping
    public MemberDto getMember() {
        //1.
        Object session = request.getSession().getAttribute("loginDto");
        //2.
        if (session != null) {
            return (MemberDto) session;
        }
        return null;

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
            return true;
        }

        return false;

    }



    //5. 로그인  (두가지 방법있음. 1017 3시40분)
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
        request.getSession().setAttribute("loginDto",null);
        return false;
    }


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






}
