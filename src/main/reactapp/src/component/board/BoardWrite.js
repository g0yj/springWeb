import axios from 'axios';


export default function BoardWrite( props ){
    const boardWrite=(e)=>{
        //1. 폼 가져오기 (폼전송: controller확인)
        let boardForm = document.querySelectorAll('.boardForm')[0];
        let boardFormData = new FormData(boardForm);
        //2. axios 전송
        axios.post("/board",boardFormData)
            .then(r=>{
                console.log(r)
                if(r.data){ alert('글등록 성공'); window.location.href="/board/list";
                }else {alert('글등록실패')}

                })
    }


    return(<>
        <div>
            <h3>게시물쓰기</h3>
            <form className="boardForm">
                <input type="text" placeholder="제목" name="btitle"/> <br/>
                <textarea placeholder="내용" name="bcontent"></textarea> <br/>
                <input type="file" name="file" /><br/>
                <button type="button" onClick={boardWrite}>등록</button>
            </form>
        </div>

        </>)
    }

/*
재목
컨텐츠
첨부파일 - 별도의 라이브러리 필요 x

*/