package ezenweb.controller;

import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}