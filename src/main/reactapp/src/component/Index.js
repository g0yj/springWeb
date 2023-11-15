/*
    Index : 여러 컴포넌트들을 연결하는 최상위 컴포넌트
        - 가상URL을 정의해서 컴포넌트를 연결하는 공간/컴포넌트

*/
import { BrowserRouter , Routes , Route , Link } from "react-router-dom"
import{useState,useEffect,useRef,useContext,createContext} from 'react'


import Header from './Header'
import Main from './Main.js'
import Footer from './Footer'

import ExampleList from'./example/ExampleList';
import 컴포넌트1 from './example/day01/1_컴포넌트'
import 컴포넌트2 from './example/day01/2_컴포넌트'
import 컴포넌트3 from './example/day01/3_컴포넌트'
import 컴포넌트4 from './example/day01/4_컴포넌트'
import CSS컴포넌트 from './example/day02/1_CSS적용컴포넌트'
import TodoList from './example/day02/todoList'
import CommentList from './example/day02/CommentList'
import Axios컴포넌트 from './example/day04/1_Axios컴포넌트'
import Login from './member/login'
import Signup from './member/signup'
import Info from './member/info'
import BoardList from'./board/BoardList'
import BoardWrite from'./board/BoardWrite'
import BoardView from './board/BoardView'
import BoardUpdate from './board/BoardUpdate'
import ProductAdmin from'./product/ProductAdmin'
/*mui 라이브러리 호출*/
import {useSnackbar} from 'notistack'; //npm i notistack 설치


/*리액트 Context 변수*/
export const SocketContext = createContext();


export default function Index( props ){
    /*mui 라이브러리 객체 호출*/
    const { enqueueSnackbar } = useSnackbar();

    //console.log(createContext()); 담기는 값 확인으 위해 찍어봄

    //* 웹소켓 객체를 담은 useRef 변수 생성
   let clientSocket = useRef(null);
   //1. 만약 웹솟켓객체가 비어 있으면,
   if(!clientSocket.current) {//Ref상태변수는 current 속성에 초기값을 저장하고 '객체'를 가지는 구조
        //1-1 서버소켓과 연결
        clientSocket.current= new WebSocket("ws://localhost:80/chat");

        //1-2 클라이언트 소켓의 각 기능/메소드 들의 기능 구현
         //1. 서버소켓과 연동 성공 했을 때 이후 메소드 정의
        clientSocket.current.onopen=(e)=>{console.log(e)}
         //2. 서버소켓과 세션 오류가 발생했을 때 이후 메소드 정의
        clientSocket.current.onerror=(e)=>{console.log(e)}
         //3. 서버소켓과 연동이 끊겼을 때
        clientSocket.current.onclose=(e)=>{console.log(e)}
         //4. 서보소켓으로부터 메세지를 받았을때
        clientSocket.current.onmessage=(e)=>{

            enqueueSnackbar(e.data, { variant: 'success' });
        }
   }





    return(<>
        <div className="webContainer">
            <SocketContext.Provider value={clientSocket}> {/*전달 내용과 전달할 컴포넌트들의 범위 지정*/}
                <BrowserRouter>
                    <Header/>
                        <Routes >
                        {/*MAIN*/}
                            <Route path='/' element = { <Main /> } />

                        {/*EXAMPLE*/}
                        <Route path='/example' element = { <ExampleList /> } />

                            <Route path='/example/day01/컴포넌트1' element={<컴포넌트1/>}/>
                            <Route path='/example/day01/컴포넌트2' element={<컴포넌트2/>}/>
                            <Route path='/example/day01/컴포넌트3' element={<컴포넌트3/>}/>
                            <Route path='/example/day01/컴포넌트4' element={<컴포넌트4/>}/>
                            <Route path='/example/day02/CSS컴포넌트' element={<CSS컴포넌트/>}/>
                            <Route path='/example/day02/todoList' element={<TodoList/>}/>
                            <Route path='/example/day02/CommentList' element={<CommentList/>}/>
                            <Route path='/example/day04/Axios컴포넌트' element={<Axios컴포넌트/>}/>

                        {/* MEMBER*/}
                            <Route path='/login' element={<Login/>}/>
                            <Route path='/signup' element={<Signup/>}/>
                            <Route path='/info' element={<Info/>}/>

                        {/*Board*/}
                        <Route path='/board/list' element = { <BoardList /> } />
                        <Route path='/board/write' element = { <BoardWrite /> } />
                        <Route path='/board/view' element = { <BoardView /> } />
                        <Route path='/board/update' element = { <BoardUpdate /> } />

                        {/*Product*/}
                        <Route path='/admin/product' element={<ProductAdmin/>}/>
                        </Routes >
                    <Footer/>
                </BrowserRouter>
            </SocketContext.Provider>
        </div>
    </>)
            }
