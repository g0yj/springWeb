import styles from '../../css/login.css'
import axios from 'axios'

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


    return(<>
            <div className="loginContainer">
                <h3>회원가입페이지</h3>
                <form>
                    이메일[아이디]: <input type="text" className="memail"/><br/>
                    비밀번호: <input type="password" className="mpassword"/><br/>
                    비밀번호확인: <input type="password" className="mpassword2"/><br/>
                    이름: <input type="text" className="mname"/><br/>
                    전화번호: <input type="text" className="mphone"/><br/>
                    <button type="button" onClick={onSignup}>회원가입</button>
                </form>
            </div>
        </>)
    }
