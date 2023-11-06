import { BrowserRouter , Routes , Route , Link } from 'react-router-dom'
import{useSearchParams} from 'react-router-dom'
import {useState, useEffect} from 'react';
import axios from 'axios';

console.log('상세페이지열기')

export default function BoardView( props ){

//==== 1. 개별 게시물 출력
    //1. HTTP 경로사의 쿼리스트링 매개변수 호출
    const[searchParms,setSearchParams]=useSearchParams();
    let bno= searchParms.get('bno');
    //2.현재 게시물의 정보를 가지는 상태관리 변수
    const [board,setBoard]=useState({})

    // 유효성검사를 위한 현재 로그인된 회원번호 변수
    const login=JSON.parse(sessionStorage.getItem('login_token'));
    const loginMno=login!=null?login.mno:null;
    //3. 개별 게시물 axios [컴포넌트가 최초 한번 실행했을 때 useEffect()]
    const onGet=e=>{
        axios.get("/board/doGet",{params:{bno:bno}})
            .then(r=>{setBoard(r.data)})
}
    useEffect( ()=>{onGet()} , [] )




//==== 3.삭제 axios
    const onDelete=(e)=>{
        axios.delete('/board',{params:{bno:bno}}) //쿼리스트링으로 전달
                .then((r)=>{
                    if(r.data){alert('게시물 삭제 성공'); window.location.href="/board/list"}
                    else{alert('삭제 실패')}
                })
    }


    return(<>
        <div>
            <h3>{bno}번 개별 게시물 </h3>
            <div>{board.btitle}</div>
            <div>{board.bcontent}</div>
            {/*삭제와 수정은 본인만 가능 - 삼항연산자를 이용한 컴포넌트 출력*/}
            {
                board.mno==loginMno?
                (<>
                    <button type="button" onClick={onDelete}>삭제</button>
                    <Link to={'/board/update?bno='+bno}><button type="button">수정</button></Link></>)
                :(<></>)
            }


        </div>
        </>)
    }
