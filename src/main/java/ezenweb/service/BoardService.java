package ezenweb.service;

import ezenweb.model.dto.BoardDto;
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

    @Transactional
    public boolean write(BoardDto boardDto){
        //1. FK 키의 엔티티를 찾는다
                //[FK로 사용할 PK를 알고 있어야함. 세션이나 매개변수로 가져오기]
                //-> 작성자PK=로그인 세션
                //-> 카테고리번호 = 입력받은 DTO, 매개변수로 가져옴.
        Optional<MemberEntity>memberEntityOptional=memberEntityRepository.findById(memberService.getMember().getMno());
         //유효성검사
        if(!memberEntityOptional.isPresent()){
            return false;
        }

        BoardEntity boardEntity = boardEntityRepository.save(boardDto.saveToEntity());

        boardEntity.setMemberEntity(memberEntityOptional.get());

        memberEntityOptional.get().getBoardEntityList().add(boardEntity);

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
