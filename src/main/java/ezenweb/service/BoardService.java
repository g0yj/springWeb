package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.dto.PageDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PageDto getAll(int page,String key,String keyword, int view){
        System.out.println("BoardService.getAll");
        System.out.println("page = " + page);

        /*JPA 페이징처리 라이브러리
            1. Pageable: 페이지 인터페이스(구현체 필요!!)
                        사용이유: Repositry인터페이스가 페이징 처리 시 사용하는 인터페이스가 pageable임. 반환 타입 확인해보셈
            2. 구현체 :PageRequest
                        of (현재페이지, 페이지별게시물수)
                            현재페이지는 0부터 시작, 만약 2일때 페이지마다 게시물 2개씩 출력
            3. Page :list와 마찬가지로 여러개의 객체를 저장하는 타입
                       + 추가적인 함수를 지원 (인터페이스 타고 들어가보면 확인 가능)
                         ㄴ -getTotalPages()
        */
                            //PageRequest.of(,,,) 반환타입 보면 sort도 제공함. 이게 정렬하는 방법임. 우린 @query에서 오름차순으로 정렬했기 때문에 여기서는 생략!!
                            //PageRequest.of(page-1,view,Sort.by(Sort.Direction.desc,"cdate"));
        Pageable pageable= PageRequest.of(page-1,view);
                                //구현체인데 new를 쓰지 않는 이유?! .of에 집중!! PageRequest 안에 of는 static으로 만들어져있음.

        //1. 모든 게시물 호출
        //List<BoardEntity> boardEntities = boardEntityRepository.findAll();
        //반환값에 맞게 형변환.
        //Page<BoardEntity> boardEntities = boardEntityRepository.findAll(pageable);
        Page<BoardEntity> boardEntities=boardEntityRepository.findBySearch(key,keyword,pageable);
        System.out.println("여기1 > "+boardEntities);

        //2. List<BoardDto>로 변환
        List<BoardDto> boardDtos=new ArrayList<>();
        boardEntities.forEach(e->{
            boardDtos.add(e.allToDto());
        });
        //3. 총 페이지수
        int totalPage=boardEntities.getTotalPages();
        //4. 총 게시물 수
        long totalCount=boardEntities.getTotalElements();//요소: 성분/물체의 하나의 단위(=게시물 1개)
        //5. 페이지 dto 구성해서 axios에 전달
        PageDto pageDtos = PageDto.builder()
                .boardDtos(boardDtos)
                .totalCount(totalCount)
                .totalPages(totalPage)
                .build();

        return pageDtos;

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

    //개별 게시물 출력
    @Transactional
    public BoardDto doGet(int bno){
        Optional<BoardEntity> optionalBoardEntity=boardEntityRepository.findById(bno);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
                //조회수 증가
            boardEntity.setBview(boardEntity.getBview()+1);
            BoardDto boardDto = boardEntity.allToDto();
            return boardDto;
        }
        return null;}


}
