
import styles from '../../css/login.css'
import axios from 'axios'
import{useState, useEffect} from 'react';

export default function Info( props ){ //로그인 상태별로, 회원권한(mrole별로 페이지 접근 제한 할 수 있는 방법 있음!!!아직 그거 사용 안함.)
    //axios로부터 전달 받은 로그인된 회원정보를 상태변수에 저장
    const [member,setMember]=useState({}) //객체로 받을거라 중괄호{}씀. null도 가능

    //로그인 정보 호출해서 출력[최초1번실행]
    useEffect(()=>{
        axios
            .get('/member/get')
            .then(r=>{setMember(r.data)})
    },[])

    //1. 이름 입력했을때 상태변경
    const mnameInputChange =(e)=>{
        console.log(e.target.value); //onChange 이벤트를 실행한 주체자 [e.target]의 값 호출[.value]
        let mnameInput=e.target.value;
        /*
        문제점발생!!!!!!!!!!!
         setMember(mnameInput); // 문제발생.
         이렇게 넣으면 8번째 줄에 대입되면서 값이 다 바뀜.. 그럼 이메일 바꾸기 할 때 문제가 됨.
        */

      /*
        문제점발생!!!!!!!!!!!!!
        //member 상태변수 특정 속성만 교체 필요. member 객체 중에 mname 속성만 교체해야됨!! <-member 상태변수에 전체 수정이 아닌 member 상태변수 내 특정 속성만 변경
        let changeMember= member;//기존 객체를 새로운 객체에 대입
        changeMember.mname=mnameInput; //객체의 특정 속성만 새로운 값 대입
        setMember(changeMember); //수정된 새로운 객체를 상태변수에 대입
        setState()는 상태변수의 주소값이 변경되어야 반응(랜더링함)에 유의!
        */

       /*
        let changeMember={...member}; //새로운 객체 만들기
        //!!: 1.객체복사방법{...객체명} , 2.배열복사방법[...배열명] 등등  <-변경할 때는 복사해서 가져가야됨. 기존걸 가져가는게 아님!!
        // ... : Spread Operator 얕은 복제
        //{...객체명,속성명:값} //복사할 때 해당 속성명이 있으면 수정, 없으면 대입
        changeMember.mname=mnameInput; //객체의 특정 속성만 새로운 값 대입
        setMember(changeMember);//수정된 새로운 객체를 상태변수에 대입
       */
        //위에 3가지를 하나로 줄인것. 가장 많이 사용하는 방법 .
        setMember({...member,mname:mnameInput})

    }
    //2. 전화번호변경[바로..이벤트 속성 처리] : 지역변수 안하고 62번째줄에 바로~!


    //3. 회원탈퇴
    const onDelete =(e)=>{
        if(window.confirm('정말 탈퇴?')){//확인버튼 누를때,
            axios   //상태변수에 있는 회원번호를 쿼리스트링방식 형식으로 전달
                .delete("/member/delete",{params:{mno:member.mno}})
                .then(r=>{
                    if(r.data){
                        alert('탈퇴성공')
                        sessionStorage.removeItem('login_token'); //로그인 세션 제거
                        window.location.href="/"
                    }else{alert('탈퇴실패')}
                })
        }
    }

    //4. 회원수정 [service보고 결정. mno,mname,mpassword,mphone / @requestBody]
        //기존 비밀번호가 일치한지에 대한 유효성검사는 js에서 하면 안됨(노출되면안되느정보임)!!! 백엔드가 할 일임
        //js는 새로운 비밀번호 두개가 일치한지만 유효성 검사 하면됨.(아직 저장되기 전이니까 노출해도됨!)

    //입력받은 패스워드 값을 저장하는 상태변수
    const [newPassword, setNewPassword] = useState({mpassword:'', mpassword2:''})
    const onUpdate=(e)=>{console.log('수정이벤트함수실행')
                 //기존에 했던 이런 방식도 가능!! let mpassword =document.querySelector('.password').value;
         //새로운 비밀번호 2개가 일치한지 유효성검사
         if(newPassword.mpassword!=newPassword.mpassword2){ alert('비밀번호가일치하지않음'); return;}


        //1.spring service 보낼 데이터 구성. DTO로
        let info={
            mno:member.mno, //수정할 대상
            mname:member.mname, //수정할 값
            mpassword:newPassword.mpassword, // 수정할 값 . 새로 입력받은 패스워드를 전송
            mphone:member.mphone //수정할값
        }
        console.log(info) //수정할 정보들의 저장된 객체 확인

        axios
            .put("/member/put",info)
            .then(r=>{
                if(r.data){alert('수정성공'); window.location.href="/"}
                else{alert('수정실패')}
            })
    }


    return(<>
            <div className="loginContainer">
                <h3>회원정보수정</h3>
                <form>
                    이메일[아이디]: <input value={member!=null?member.memail:''} disabled type="text" className="memail" /><br/>

                    새비밀번호: <input value={newPassword.mpassword} onChange={(e)=>{setNewPassword({...newPassword, mpassword:e.target.value})}} type="password" className="mpassword"/><br/>
                    새비밀번호확인: <input value={newPassword.mpassword2} onChange={(e)=>{setNewPassword({...newPassword,mpassword2:e.target.value})}} type="password" className="mpassword2"/><br/>

                    이름: <input value={member!=null?member.mname:''} onChange={mnameInputChange} type="text" className="mname"/><br/>

                    전화번호: <input value={member!=null?member.mphone:''} onChange={(e)=>{setMember({...member,mphone:e.target.value})}} type="text" className="mphone"/><br/>

                    <button type="button" onClick={onUpdate} >정보수정</button>
                    <button type="button" onClick={onDelete} >회원탈퇴</button>
                </form>
            </div>
        </>)
    }
