import { BrowserRouter , Routes , Route , Link } from "react-router-dom"

export default function ExampleList( props ){
    return(<>
          <div
            style={{display:'flex',justifyContent: 'space-between'}}
           >
            <Link to='/example/day01/컴포넌트1'>컴포넌트1예제</Link>
            <Link to='/example/day01/컴포넌트2'>컴포넌트2예제</Link>
            <Link to='/example/day01/컴포넌트3'>컴포넌트3예제</Link>
            <Link to='/example/day01/컴포넌트4'>컴포넌트4예제</Link>
            <Link to='/example/day02/CSS컴포넌트'>CSS적용컴포넌트예제</Link>
            <Link to='/example/day04/Axios컴포넌트'>AXIOS 테스트</Link>


          </div>

    </>)
            }
