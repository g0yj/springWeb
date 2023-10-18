package ezenweb.controller;

import ezenweb.model.dto.BoardDto;
import ezenweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("")
    public boolean write(BoardDto boardDto){
                            //첨부파일 받을 때는 @RequestParam 쓰지 X
       return boardService.write(boardDto);
    }

    @GetMapping("")
    public List<BoardDto> getAll(){
        return boardService.getAll();
    }

    @PutMapping("")
    public boolean update(BoardDto boardDto){
        return boardService.update(boardDto);
    }

    @DeleteMapping("")
    public boolean delete(@RequestParam int bno){
        return boardService.delete(bno);
    }


}
