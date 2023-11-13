import { BrowserRouter , Routes , Route , Link } from 'react-router-dom'


//4. HTTP URL 경로상의 쿼리스트링 매개변수 호출 라이브러리 호출
import{useSearchParams} from 'react-router-dom'
//4. HTTP URL 경로상의 경로 매개변수 호출 라이브러리 호출
import{useParams} from 'react-router-dom';
import {useState} from 'react';

//1. 상위 컴포넌트 라우터
export default function 라우터매개변수( props ){
    return(<>
        <h3>라우터매개변수</h3>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<목록페이지/>}/>
                {/* 쿼리스트링은 매개변수인지 경로인지 구분 안해도됨  경로상에 ?가 있기 때문에 라우터 쪽에 언급 없어도됨. 링크 보낼때 ?사용해서 보냄, 받을때 useSearchParams()*/}
                <Route path="/view1" element={<상세페이지_쿼리스트링/>}/>

               {/*매개변수인지 경로인지 구분: 매개변수일때 ':' 추가 . 주소에 '/' 넣어줌. useParams() 사용*/}
                <Route path="/view2/:bno/:value" element={<상세페이지_경로/>}/>
            </Routes>
        </BrowserRouter>

        </>)
    }
//2. 상위컴포넌트
function 목록페이지(props){
    const[value,setValue]=useState('');
    return(<>
        <h3> 목록페이지.</h3>
        <input type="text" onChange={e=>{setValue(e.target.value)}}/>
        <Link to={"/view1?bno=1&value="+value}>쿼링스트링</Link> <br/>
        <Link to={"/view2/2/"+value}>경로</Link> <br/>
    </>)
}

//3. 하위 컴포넌트1
function 상세페이지_쿼리스트링(props){

        const [searchParams,setSearchParams ]=useSearchParams()
        console.log(setSearchParams)
        let bno= searchParams.get('bno');
        let value= searchParams.get('value');

    return(<>
        <h3> 상세페이지_쿼리스트링 </h3>
        bno={bno} <br/>
        value={value}
    </>)
}

//4. 하위컴포넌트2
function 상세페이지_경로(props){

        const Params =useParams()
        console.log(Params)
        let bno=Params.bno;
        let value=Params.value;

    return(<>
               <h3> 상세페이지_경로 </h3>
                bno={bno} <br/>
                value={value}
    </>)
}