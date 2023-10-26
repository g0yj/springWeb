import {Link} from 'react-router-dom';
import styles from'../css/header.css'

import axios from 'axios';
import{useState} from 'react'

export default function Header( props ){
    //1-1. 로그인 상태를 저장할 상태변수 선언
    let[login,setLogin] = useState(null);

   /* function 함수명(e) {}
        const 변수명 = (e) => {} */

      // 2번. 로그아웃
       //1.
    const logout=(e)=>{
    axios
        .get('/member/logout')
        .then(r=>{
            console.log(r.data)
            if(r.data){//로그아웃이 성공햇으면
                // window.location.reload(); //새로고침 . 로그아웃되면 리랜더링되기 때문에 새로고침 필요없긴함.. 그러나 안정성을 위해 새로고침해서 모든 걸 없애주는 걸 권장함!
                setLogin(null);
            }

            })
    }

        axios
            .get('/member/get')
            .then(r=>{
                if(r.data!=''){ //만약에 로그인이 되어 있으면
                    setLogin(r.data);
                }
            })



    return(<>
        <header>
            <h2><Link to='/'>이젠리액트</Link></h2>
            <ul>
                <li><Link to='/example'>리택트예제</Link></li>
                <li><Link to='/example/day02/todoList'>TODO</Link></li>
                <li><Link to='/example/day02/CommentList'>비회원게시판</Link></li>
                <li><Link to='/'>회원게시판</Link></li>

                {/* 삼항연산자-> 조건?참:거짓*/}

                {
                    login==null
                    ? (<>
                        <li><Link to='/login'>로그인</Link></li>
                        <li><Link to='/signup'>회원가입</Link></li>
                    </>)
                    : (<>
                        <li>{login.memail}님</li>
                        <li><Link to='/'>내정보</Link></li>
                        <li><div onClick={logout}>로그아웃</div></li>
                    </>)
                }



            </ul>
        </header>
    </>)
            }
