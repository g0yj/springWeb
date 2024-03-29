package ezenweb.controller;

import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// IOC : 제어역전(객체 관리를 스프링에게 위임<- 개발자가 편의성. (객체 공유해서 쓰려고. 서로 다른 객체 사용했을 때 발생할 수 있는 오류를 줄일 수 있음.))
// DI: 의존성 주입[스프링이 객체를 관리하기 때문에. 스프링에게 객체를 의존하고 가져다가 씀.]
@RestController
@RequestMapping("/member")
//@CrossOrigin("http://localhost:3000")
    // 교차 리소스를 공유하겠다.[해당주소=리액트주소] 임시로 쓰겠음. 추후 배포시에는 서버 두개 합칠거임.
    //HTTP헤더 [Access-Control-Allow-Origin허용]에 교차 리소스 공유
    // 리액트랑 스프링이랑 통합했다면 이 어노테이션 사용할 필요 없음!!
public class MemberController {
    @Autowired
    private MemberService memberService;

    //1. 회원가입
    @PostMapping("/post")
    public boolean postMember(@RequestBody MemberDto memberDto) {
        boolean result = memberService.postMember(memberDto);
        return result;
    }

/*
    @GetMapping("/get")
    //2. 회원정보호출(1명) ->세션을 구현 하지 않았을때
    public MemberDto getMember(@RequestParam int mno) {
        MemberDto memberDto = memberService.getMember(mno);
        return memberDto;
    }

 */

    //2. 회원정보호출 [세션구현했을때]
    @GetMapping("/get")
    public MemberDto getMember() {
        return  memberService.getMember();
    }

    //3. 회원정보 수정
    @PutMapping("/put")
    public boolean updateMember(@RequestBody MemberDto memberDto) {
        boolean result = memberService.updateMember(memberDto);
        return result;
    }

    //4. 회원삭제
    @DeleteMapping("/delete")
    public boolean deleteMember(int mno) {
        boolean result = memberService.deleteMember(mno);
        return result;
    }

/*--------------시큐리티 사용으로 로그인, 비밀번호 사용 안함!!--------------------------------------

    //5. [post] 로그인 : 요청(아이디,비번) 응답(성공/실패)
    @PostMapping("/login")
    public boolean login(@RequestBody MemberDto memberDto) {
        boolean result=memberService.login(memberDto);
        return result;
    }

    //6. [get]로그아웃
    @GetMapping("/logout")
    public boolean logout() {
        boolean result=memberService.logout();
        return result;
    }

--------------------------------------------------------------------------------------------------*/

    //7. [post]아이디찾기 - 이름/전화번호
    @PostMapping("/findId")
    public String findId(@RequestParam String mname, @RequestParam String mphone){
        System.out.println("MemberController.findId");
        return memberService.findId(mname,mphone);
    }


    //8.[post]비밀번호찾기- 이메일/전화번호
    @PostMapping("/findPw")
    public String findPw(@RequestParam String memail,@RequestParam String mphone){
        System.out.println("MemberController.findPw");
        return memberService.findPw(memail,mphone);
    }


    //9. 이메일 중복검사
    @GetMapping("/findMemail")
    public boolean getFindEmail(@RequestParam String memail){
                    //signup.js에서 파라미터로 전달함.
        System.out.println("MemberController.getFindEmail");
        System.out.println("memail = " + memail);

        return memberService.getFindEmail(memail);
    }



}


