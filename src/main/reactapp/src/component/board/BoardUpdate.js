import axios from 'axios';
import {useState, useEffect} from 'react'
import { BrowserRouter , Routes , Route , Link } from 'react-router-dom'
import{useSearchParams} from 'react-router-dom'

export default function BoardUpdate( props ){

        const[searchParms,setSearchParams]=useSearchParams();
        let bno= searchParms.get('bno');

        //1.현재 게시물의 정보를 가지는 상태관리 변수
        const [board,setBoard]=useState({})

        //2. 개별 게시물 axios [컴포넌트가 최초 한번 실행했을 때 useEffect()]
        const onGet=e=>{
            axios.get("/board/doGet",{params:{bno:bno}})
                .then(r=>{setBoard(r.data)})
    }
        useEffect( ()=>{onGet()} , [] )

        //3. 개별 게시물 수정 요청
        const boardUpdate=(e)=>{
           let boardForm = document.querySelectorAll('.boardForm')[0];
           let boardFormData = new FormData(boardForm);
            //boardFormData : 입력 받은 수정할 제목과 내용 + 수정할 게시물의 번호
            boardFormData.set('bno',bno); // 수정할 게시물 번호를 폼 속성에 추가
            axios.put("/board",boardFormData)
                 .then(r=>{
                      if(r.data){ alert('글등록 성공'); window.location.href="/board/list";
                      }else {alert('글등록실패')}
                     })

        }




    return(<>
        <div>
            <h3>게시물 수정</h3>
            <form className="boardForm">
                <input type="text" placeholder="제목" name="btitle"
                    value={board.btitle}
                    onChange={e=>{setBoard({...board, btitle:e.target.value})}}
                    /> <br/>
                <textarea placeholder="내용" name="bcontent"
                    value={board.bcontent}
                    onChange={e=>{setBoard({...board, bcontent:e.target.value})}}
                ></textarea> <br/>
                <input type="file" />
                <button type="button" onClick={boardUpdate}>등록</button>
            </form>
        </div>

        </>)
    }