//231024 11시

/*
    - HTTP 기반의 비통기 통신 함수
    Axios( React 라이브러리 )  < ----- >   AJAX( jquery라이브러리)
        1. 설치
            1. 터미널 [ alt+f12 ]
            2. 리액트 프로젝트 폴더내 설치 [ cd src/main/reactapp ]
                - 키보드 위/아래 화살표 입력시 기존 코드 표시
            3. npm i axios

        2. 특징
            1. contentType : JSON

        2. 컴포넌트에서 axios 사용하기
            1. import
                파일 상단에 import axios from 'axios';
            2. ajax vs axios
                       $.ajax({  url : '' ,  method:  , data :  , success :  , })
                        vs
                        axios.methodType( url ).then( 반환매개변수 => { } )
            3.
                - JSON객체를 HTTP BODY 형식으로 보낼때.
                axios
                    .methodType( url ,  JSON객체  )
                    .then( 반환매개변수 => { } )

                - JSON객체를 쿼리스트링 형식 보낼때.
                axios
                    .methodType( url , { params : JSON객체  } )
                    .then( 반환매개변수 => { } )


*/

import axios from 'axios'; //npm i axios 설치 후 사용 가능

export default function Axios컴포넌트( props ){


    //컴포넌트(함수)
    //1. 기본함수
    function 함수1(e){console.log(e)} //e:event변수[이벤트 발생 후 상태/결과 저장된 매개변수]
    //2. 화살표함수를 저장하는 상수(*)
    const 함수2=(e)=>{console.log(e)}
    //3. 화살표함수를 매개변수 받기
    const 함수3 =(e,data)=>{console.log(e);console.log(data);}

    //----------------AXIOS----------------------------//
    //1.GET
    function doGet(){
       // axios.메소드(url).then(리턴값=>{})
        axios
            .get('https://jsonplaceholder.typicode.com/posts')
            .then(response=>{console.log(response)})

        axios
            .get('https://jsonplaceholder.typicode.com/posts/1') //PATH
            .then(response=>{console.log(response)})

        axios
            .get('https://jsonplaceholder.typicode.com/comments?postId=1') //querystring
            .then(response=>{console.log(response)})


        axios
            .get('https://jsonplaceholder.typicode.com/comments',{params:{'postId':1}})//querystring
            .then(r=>{console.log(r.data)})

    }


    //2.POST
    function doPost(){
        let saveInfo = { title: 'foo', body: 'bar',  userId: 1, } //샘플데이터
        axios
            .post('https://jsonplaceholder.typicode.com/posts')
            .then(r=>console.log(r.data))
    }

    //3. PUT
    function doPut(){
        let updateInfo = {  id: 1,  title: 'updateFoo',  body: 'updateBar',   userId: 1 }//샘플데이터
        axios
            .put('https://jsonplaceholder.typicode.com/posts/1')
            .then(r=>console.log(r.data))
    }

    //4. DELETE
    function doDelete(){
        axios
            .delete('https://jsonplaceholder.typicode.com/posts/1')
            .then(r=>{console.log(r.data);})

    }
    return(<>
        <h3>AXIOS 테스트</h3>
        {/*jsx에서 이벤트 속성 사용. 1. 이벤트명(카멜표기법)2.{함수명} 3. {e=>함수(e,매개변수1,매개변수2)}*/}
        <button type="button" onClick={함수1}>함수1</button>
        <button type="button" onClick={함수2}>함수2</button>
        <button type="button" onClick={e=>함수3(e,3)}>함수3</button>

        <button type="button" onClick={doGet}> doGet AXIOS</button>
        <button type="button" onClick={doPost}> doPost AXIOS </button>
        <button type="button" onClick={doPut}> doPut AXIOS </button>
        <button type="button" onClick={doDelete}> doDelete AXIOS </button>

        </>)
}
