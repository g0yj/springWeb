package ezenweb.controller;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.PageDto;
import ezenweb.service.BoardService;
import ezenweb.service.FileService;
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
        System.out.println("글쓰기 컨트롤러");
        System.out.println("boardDto = " + boardDto);
                            //첨부파일 받을 때는 @RequestParam 쓰지 X, @RequestBody 사용하지 않음.
                            // @RequestBody는 json 전송이고 form은 x-www-form으로 전송됨. 어노테이션 사용 시, null값으로 들어감.
       return boardService.write(boardDto);
    }

    @GetMapping("")
    public PageDto getAll(@RequestParam int page , //js에서 쿼리스트링으로 보냈으니까 @RequestParam으로 받음
                          @RequestParam String key,
                          @RequestParam String keyword,
                          @RequestParam int view){
        System.out.println("BoardController.getAll");
        System.out.println("page = " + page);
        return boardService.getAll(page,key,keyword,view);
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

    // 첨부파일 다운로드 요청
    @Autowired private FileService fileService;
    @GetMapping("/filedownload")
    public void filedownload(@RequestParam String uuidFile){
        fileService.fileDownload((uuidFile));
    }
}
