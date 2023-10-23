/*
    - 라우터 : 가상 url 만들기
        -라우터: 연결 경로를 자동으로 전환 해주는 기계
        - 리액트 라우터: 가상 경로[URL]를 만들어서 컴포넌트를 전환해주는 라이브러리
        - 설치
            1. https://www.npmjs.com/
            2. router-dom 검색
            3.
        - 다른 컴포넌트에서 컴포넌트(페이지)전환
            1. <a href='(서버/라우터)경로'></a>
            2.

*/
import { BrowserRouter , Routes , Route , Link } from "react-router-dom" //라우터라이브러리 설치 후 가능
import 컴포넌트1 from '../day01/1_컴포넌트' // 다른 폴더에 있는 컴포넌트 호출
import 컴포넌트2 from '../day01/2_컴포넌트' // 다른 폴더에 있는 컴포넌트 호출
import 컴포넌트3 from '../day01/3_컴포넌트' // 다른 폴더에 있는 컴포넌트 호출
import 컴포넌트4 from '../day01/4_컴포넌트' // 다른 폴더에 있는 컴포넌트 호출


export default function 라우터컴포넌트( props ){
    return(<>
        <BrowserRouter>
            <고정컴포넌트/>{/**/}
            <Routes >
                <Route path='/day01/컴포넌트1' element = { <컴포넌트1 /> } />
                <Route path='/day01/컴포넌트2' element = { <컴포넌트2 /> } />
                <Route path='/day01/컴포넌트3' element = { <컴포넌트3 /> } />
                <Route path='/day01/컴포넌트4' element = { <컴포넌트4 /> } />
            </Routes >
        </BrowserRouter>
    </>)
}

function 고정컴포넌트( props ){
    return(<>
        <h3>고정컴포넌트</h3>

        <h3>a태그 사용</h3>
        <a href='/day01/컴포넌트1'>컴포넌트1</a>
        <a href='/day01/컴포넌트2'>컴포넌트2</a>
        <a href='/day01/컴포넌트3'>컴포넌트3</a>
        <a href='/day01/컴포넌트4'>컴포넌트4</a>

        <h3>Link 사용</h3>
        <Link to='/day01/컴포넌트1'>컴포넌트1</Link>
        <Link to='/day01/컴포넌트2'>컴포넌트2</Link>
        <Link to='/day01/컴포넌트3'>컴포넌트3</Link>
        <Link to='/day01/컴포넌트4'>컴포넌트4</Link>

        </>)}
