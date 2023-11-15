import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

//3. 내가 만든 컴포넌트(jsx파일 내 함수) 호출
//import 컴포넌트명 from 'jsx파일경로'
//import 컴포넌트1 from './component/example/day01/1_컴포넌트.jsx'
//import 컴포넌트2 from './component/example/day01/2_컴포넌트.jsx'
//import 컴포넌트3 from './component/example/day01/3_컴포넌트.jsx'
//import 컴포넌트4 from './component/example/day01/4_컴포넌트.jsx'
//import 도서목록 from './component/example/day01/과제1_도서목록.jsx'
//import CSS컴포넌트 from './component/example/day02/1_CSS적용컴포넌트.js'
//import CommentList from './component/example/day02/CommentList.js'
//import TodoList from './component/example/day02/todoList.js'
//import 라우터컴포넌트 from './component/example/day03/1_라우터컴포넌트.js'
import Index from './component/Index.js'
//import 상태관리컴포넌트 from './component/example/day05/1_상태관리컴포넌트.js'
//import 라우터매개변수 from './component/example/day06/1_라우터매개변수.js'

/*mui 라이브러리 호출*/
import { SnackbarProvider } from 'notistack';

//1. index.html에 <div id="root"> dom객체 호출
const root = ReactDOM.createRoot(document.getElementById('root'));
//2. 리액트랜더링(JSX-->HTML로 변환 )
   //<React.StrictMode> :예외처리 컴포넌트
root.render(<React.StrictMode>
    <SnackbarProvider maxSnack={5}>
      <Index />
    </SnackbarProvider>

 </React.StrictMode>);
//root.render( <라우터매개변수/> );


//root.render(<Index/>);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
