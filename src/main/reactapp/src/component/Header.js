//231027 9시55분

import {Link} from 'react-router-dom';
import styles from '../css/header.css'

import axios from 'axios';
import{useState,useEffect} from 'react'

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

               //세션제거
               sessionStorage.removeItem('login_token')

                setLogin(null);
            }

            })
    }
        // - 회원정보호출[로그인 여부 확인] - js에서 로그인 기능 구현하는 방법(브라우저 자체에서 세션을 제공)
       //-----------------컴포넌트 생성될 때 1번-------------------------//
                    //useEffect(()=>{},[]) 안에 axios 안 넣으면 무한루프빠짐
        useEffect(()=>{

        axios
            .get('/member/get')
            .then(r=>{
                if(r.data!=''){ //만약에 로그인이 되어 있으면
                   //브라우저 세션/쿠키
                    //localstorage : 모든 브라우저 탭/창공유, 브라우저가 꺼져도 유지, 자동로그인 기능, 로그인상태유지(로그인여부)
                    //sessionstorage : 탭/창 종료 시 사라짐. 페이지전환은 유지됨! 로그인여부(로그인상태저장)

                //f12에서 세션 저장된게 보고 싶다면 이걸 해줘야되는거임.
                    //세션/쿠키 저장: .setItem(key,value)
                    //세션/쿠키 호출: .getItem(key)
                    //세션/쿠키 삭제 : .remove(key)
                    sessionStorage.setItem('login_token', JSON.stringify(r.data));
                    setLogin(JSON.parse(sessionStorage.getItem('login_token')));

                    setLogin(r.data);
                }
            })

        },[])
        //----------------------------------------------------------//


    return(<>
        <header>
            <h2><Link to='/'>이젠리액트</Link></h2>
            <ul>
                <li><Link to='/example'>리택트예제</Link></li>
                <li><Link to='/example/day02/todoList'>TODO</Link></li>
                <li><Link to='/example/day02/CommentList'>비회원게시판</Link></li>
                <li><Link to='/board/list'>회원게시판</Link></li>
                <li><Link to='/admin/product'>제품관리</Link></li>


                {/* 삼항연산자-> 조건?참:거짓*/}

                {
                    login==null
                    ? (<>
                        <li><Link to='/login'>로그인</Link></li>
                        <li><Link to='/signup'>회원가입</Link></li>
                    </>)
                    : (<>
                        <li>{login.memail}님</li>
                        <li><a href='/info'>내정보</a></li> {/*Link사용하면 라우터 전달로 스프링에서 관리할 수 있는 주소가 아님. 시큐리티를 거치지 않고 지나가기 때문에 권한 안먹힘..!! link쓰면 권한 되지 않아도 403오류없이 들어가짐.*/}
                        <li><div onClick={logout}>로그아웃</div></li>
                    </>)
                }



            </ul>
        </header>
    </>)
            }

/*
    <>  </>
 : 리액트의 Fragment 요소를 나타냄. Fragment는 부모 요소 없이 여러 요소를 그룹화할 때 사용.


*/