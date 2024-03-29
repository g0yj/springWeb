import {Link} from 'react-router-dom' //Link 사용을 위해 꼭 넣어줘야
import styles from '../../css/login.css'
import axios from 'axios'


export default function Login( props ){
    //1. 로그인 버튼을 클릭했을 때
    function onLogin(e){
        console.log(e);console.log('이벤트실행')
        //2. axios를 이용한 restApi로 spring Controller 데이터 전달

            //시큐리티는 폼전송으로만 해야하기 때문에 JSON으로 보냈을때 아이디값이 넘어가지 않음. 66번째줄일 경우.
            //3-1. 데이터구성[JSON]]
             let info={
                memail: document.querySelector('.memail').value,
                mpassword: document.querySelector('.mpassword').value
             };

             //3-2 데이터구성[form전송]
             let loginForm=document.querySelectorAll('.loginForm')[0]
             let loginFormData = new FormData(loginForm)
             console.log(info)
             //4. AXIOS통신 [controller 매핑 확인]
             axios
                //.post('http://localhost:80/member/login',info) //리액트스프링 통합했으면 아래 코드로 바꿀 수 있음.
                .post('/member/login',loginFormData)
                .then(r=>{
                    if(r.data){
                        alert('로그인성공');
                        window.location.href='/'
                    }
                    else {
                        alert('로그인실패')
                    }
                });
             //CORS policy오류 발생!!
                //해결방법 : 스프링 controller 클래스에  + @CrossOrigin("http://localhost:3000")
    }



    return(<>
            <div className="loginContainer">
                <h3>로그인페이지</h3>
                <form className='loginForm'>
              아이디 <input
                      type="text"
                      placeholder='email address'
                      className='memail' name='memail' id='memail'/>

                  비밀번호 <input type="password"
                      placeholder='password'
                      className='mpassword' name='mpassword' id='mpassword' />

                  <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
                  <button onClick={ onLogin } type="button">로그인</button>

                  <a href="/oauth2/authorization/kakao">카카오 1초 로그인</a>
                  <a href="/oauth2/authorization/naver">네이버 1초 로그인</a>
                  <a href="/oauth2/authorization/google">구글 1초 로그인</a>
                </form>
            </div>
        </>)
   }


 /*

     // 1. AXIOS 이용한 로그인 처리 형식
         <form>
             아이디 <input
                 type="text"
                 placeholder='email address'
                 className='memail' />

             비밀번호 <input type="password"
                 placeholder='password'
                 className='mpassword' />

             <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
             <button onClick={ onLogin } type="button">로그인</button>
         </form>


      <form action="전송할HTTP주소" method="HTTP메소드">
      // 2.
      <form action="/member/login" method="post">
          아이디 <input type="text" placeholder='email address' name='memail' />
          비밀번호 <input type="password"  placeholder='password' name='mpassword' />
          <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
          <button type="submit">로그인</button>
      </form>


      여기서 보내면 config로 전달됨.
 */
