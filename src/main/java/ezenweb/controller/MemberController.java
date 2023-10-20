package ezenweb.controller;

import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// IOC : 제어역전(객체 관리를 스프링에게 위임<- 개발자가 편의성. (객체 공유해서 쓰려고. 서로 다른 객체 사용했을 때 발생할 수 있는 오류를 줄일 수 있음.))
// DI: 의존성 주입[스프링이 객체를 관리하기 때문에. 스프링에게 객체를 의존하고 가져다가 씀.]
@RestController
@RequestMapping("/member")
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


}


