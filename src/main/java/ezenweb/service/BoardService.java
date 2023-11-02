package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardEntityRepository boardEntityRepository;
    @Autowired//write() 에서 사용
    private MemberService memberService;
    @Autowired
    private MemberEntityRepository memberEntityRepository;

    @Transactional//양방향에서 setter쓰니까 트랜잭셔널 꼭 해줘야됨!!!!!!! ()
    public boolean write(BoardDto boardDto){
     //1. FK 키의 엔티티를 찾는다(로그인된 멤버찾기)======================================================
                //[FK로 사용할 PK를 알고 있어야함. 세션이나 매개변수로 가져오기]
                //-> 작성자PK=로그인 세션
                //-> 카테고리번호 = 입력받은 DTO, 매개변수로 가져옴.
            //1.로그인된 멤버 엔티티 찾기
        MemberDto loginDto= memberService.getMember();
        if(loginDto==null){return false;}
            //2. 로그인된 회원정보를 가진 pk엔티티 찾기
        Optional<MemberEntity>memberEntityOptional=memberEntityRepository.findById(memberService.getMember().getMno());
         //유효성검사(로그인이 안된 상태는 글쓰기 실패)
        if(!memberEntityOptional.isPresent()){
            return false;
        }
      //로그인된 멤버찾기 끝================================================================================================
  // 단방향 저장[게시판엔티티등록. 게시물 엔티티에 회원엔티티 넣어주기]
        //1.게시물생성[pk에 해당하는 레코드 생성]
        BoardEntity boardEntity = boardEntityRepository.save(boardDto.saveToEntity());
        //2. 생성된 게시물에 작성자엔티티 넣어주기[ fk 넣어주기]
        boardEntity.setMemberEntity(memberEntityOptional.get());
   //단반향 저장끝
        //양방향 저장
        memberEntityOptional.get().getBoardEntityList().add(boardEntity);
        //양방향저장끝

        if(boardEntity.getBno()>=1){return true;}
        return false;

    }


    @Transactional
    public List<BoardDto> getAll(){
        //1. 모든 게시물 호출
        List<BoardEntity> boardEntities = boardEntityRepository.findAll();
        //2. List<BoardDto>로 변환
        List<BoardDto> boardDtos=new ArrayList<>();
        boardEntities.forEach(e->{
            boardDtos.add(e.allToDto());
        });
        return boardDtos;
    }


    @Transactional//(필수)
    public boolean update(BoardDto boardDto){
        //1. 수정할 엔티티 찾기 [bno] 이용
        Optional<BoardEntity> optionalBoardEntity=boardEntityRepository.findById(boardDto.getBno());
        //2. 만약 수정할 엔티티가 존재하면
        if(optionalBoardEntity.isPresent()){
            //3. 엔티티 꺼내기
            BoardEntity boardEntity = optionalBoardEntity.get();
            //4. 엔티티 객체를 수정하면 테이블 내 레코드도 같이 수정
            boardEntity.setBtitle(boardDto.getBtitle());
            boardEntity.setBcontent(boardDto.getBcontent());
            boardEntity.setBfile(boardDto.getBfile());

        }
        return true;
    }


    @Transactional
    public boolean delete( int bno){
        //1. 엔티티 호출
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);
        if(optionalBoardEntity.isPresent()){
            boardEntityRepository.deleteById(bno);
            return true;
        }

        return true;
    }

}
