package ezenweb.controller;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.PageDto;
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
    public PageDto getAll(@RequestParam int page ,@RequestParam String key,@RequestParam String keyword){
        System.out.println("BoardController.getAll");
        System.out.println("page = " + page);
        return boardService.getAll(page,key,keyword);
    }

    @PutMapping("")
    public boolean update(BoardDto boardDto){
        return boardService.update(boardDto);
    }

    @DeleteMapping("")
    public boolean delete(@RequestParam int bno){
        return boardService.delete(bno);
    }

    @GetMapping("doGet")
    public BoardDto doGet(@RequestParam int bno){return boardService.doGet(bno);}
                            //js에서 파라미터로 들어옴
}
