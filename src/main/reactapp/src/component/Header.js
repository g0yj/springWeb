import {Link} from 'react-router-dom';
import styles from'../css/header.css'

import axios from 'axios';

export default function Header( props ){

    //-회원정보 호출[로그인 여부 확인]
    axios
        .get('/member/get')
        .then(r=>{console.log(r.data)})

    return(<>
        <header>
            <h2><Link to='/'>이젠리액트</Link></h2>
            <ul>
                <li><Link to='/example'>리택트예제</Link></li>
                <li><Link to='/example/day02/todoList'>TODO</Link></li>
                <li><Link to='/example/day02/CommentList'>비회원게시판</Link></li>
                <li><Link to='/'>회원게시판</Link></li>

                <li><Link to='/login'>로그인</Link></li>
                <li><Link to='/signup'>회원가입</Link></li>
                <li><Link to='/'>로그아웃</Link></li>

            </ul>
        </header>
    </>)
            }
