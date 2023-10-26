//231026 2시 . 이메일 중복검사 흐름

import styles from '../../css/login.css'
import axios from 'axios'
import{useState} from 'react';

export default function Signup( props ){

    //1. 회원가입 버튼 클릭햇을때
    const onSignup=(e)=>{
        console.log(e);console.log('이벤트실행')
        //2. 입력 받을 데이터 구성
        let info={
            memail:document.querySelector('.memail').value,
            mpassword:document.querySelector('.mpassword').value,
            mname:document.querySelector('.mname').value,
            mphone:document.querySelector('.mphone').value,
        }
        console.log('회원가입정보받음'+info)
        axios
            //.post('http://localhost:80/member/post',info)//리액트스프링 통합했으면 아래 코드로 바꿀 수 있음.
            .post('/member/post',info)
            .then(r=>{console.log(r)
                if(r.data){
                    alert('회원가입성공')
                    window.location.href='/login'   //get방식요청!
                    }
                else{alert('회원가입실패')}
            })



    }

    //2. 이메일 중복검사[이메일 입역할 때마다]
    let [memail,setMemail]=useState('') //import {useState} from 'react';
    let[memailCheck,setMemailCheck]=useState('')

    const emailInputChange=(e)=>{
    console.log('아이디유효성검사함수실행')
       /*
       1. 기존방법
        let memail =document.querySelector('.memail').value; console.log(memail)
        */

        //2. 새로운방법 [useState 사용]
        let memailInput=e.target.value;
        console.log('입력받은값> '+memailInput);

        setMemail(memailInput);

        //-------------AXIOS-----------------------//
        axios
            .get('/member/findMemail',{params:{'memail':memailInput}}) //쿼리스트링
            .then(r=>{
                console.log('아이디유효성검사 통신 성공')
                if(r.data){setMemailCheck('사용중인 아이디')}//중복
                else{setMemailCheck('사용가능한아이디')}//중복아님

            })

    }

    return(<>
            <div className="loginContainer">
                <h3>회원가입페이지</h3>
                <form>
                    이메일[아이디]: <input type="text" className="memail" onChange={emailInputChange}/><br/>
                    <div>{memailCheck}</div>
                    비밀번호: <input type="password" className="mpassword"/><br/>
                    비밀번호확인: <input type="password" className="mpassword2"/><br/>
                    이름: <input type="text" className="mname"/><br/>
                    전화번호: <input type="text" className="mphone"/><br/>
                    <button type="button" onClick={onSignup}>회원가입</button>
                </form>
            </div>
        </>)
    }
