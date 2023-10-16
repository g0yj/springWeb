package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberEntityRepository memberRepository;

    //1. 회원가입
    @Transactional//트랜잭션: 여러개 sql을 하나의 최소 단위로[성공/실패!! 함수 내 일부 sql만 성공은 불가능]
    public boolean postMember(@RequestBody MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        //1. dto->entity 변경 후 repository를 통한 insert  후 결과 entity받기
       MemberEntity memberEntity= memberRepository.save(memberDto.toEntity());

        //2. insert 된 엔티티 확인 후 성공/ 실패 유무
        if(memberEntity.getMno()>=1){//만약 회원번호가 0보다 크면 (auto_increqment 적용됨)
            return true;}
        return false;
    }

    //2. 회원정보호출
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

    //3. 회원정보 수정
    @Transactional
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



}
