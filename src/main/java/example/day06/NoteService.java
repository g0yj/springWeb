package example.day06;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Service 사용처 - > Contoroller
@Service
public class NoteService {
    @Autowired
    NoteEntityRepository noteEntityRepository;

    //1.C
    @Transactional
    public boolean bWrite(NoteDto noteDto){

        //1.dto->엔티티로 변경

        noteEntityRepository.save(noteDto.toEntity());
        return false;
    }

    //2.R
    @Transactional
    public List<NoteDto> bList(){
       //1.모든 엔티티를 호출
        List<NoteEntity> entities = noteEntityRepository.findAll();
        //2. 모든 엔티티 리스트-> dto 리스트 변환
        List<NoteDto> noteDots=new ArrayList<>();
        //3.엔티티->dto 변경 후 히르스테 담기
        entities.forEach(e->{noteDots.add(e.toDto());});
            return noteDots;

    }

    //3.U
    //@Transactional: 해당 주입된 함수 내에서 사용중인 sql를 트랜잭션 단위로 적용
    @Transactional //트랜잭션: 하나(여러)작업들을 묶어서 최소단위 업무처리
    public boolean bUpdate(NoteDto noteDto){
       //1.pk 번호에 해당하는 엔티티 찾기 (엔티티를 포장[Optional 안전=null방지] 해서 반환)
       Optional<NoteEntity> optionalNoteEntity =
                                noteEntityRepository.findById(noteDto.getNo());

       //2. 포장 내 내용물이 있는지 체크 = ㅏ안전하게 검토
        if(optionalNoteEntity.isPresent()){
            //3. 포장내 내용물 꺼내기 = 포장된 고셍서 엔티티 꺼내는 과정 .get();
            NoteEntity noteEntity=optionalNoteEntity.get();
            //4.수정 :
            noteEntity.setTitle(noteDto.getTitle());//update
            noteEntity.setWriter(noteDto.getWriter());//update

        }
       return false;
    }

    //4.D
    @Transactional
    public boolean bDelete(int no){
        noteEntityRepository.deleteById(no);
        return false;
    }



}
