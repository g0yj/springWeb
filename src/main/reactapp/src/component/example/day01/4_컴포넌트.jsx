/*
    JSX의 규칙
        1. return (<>HTML문법</>)    : 두 줄 이상일 때, JSX 구역 표시 해줘야
        2. return (<>{JS문법}</>)       :JSX 구역에서 JS 문법을 사용할 때 {JS문법 }
*/
function 컴포넌트4(){
    return(<>
        <input type="text" value="데이터"/>
        <내가만든태그속성 이름={"유재석"} 나이={30}/>
        <내가만든태그속성 이름="강호동" 나이={40}/>
     </>)
}

function 내가만든태그속성(props){ //props:컴포넌트의 매개변수(객체로들어옴)
    //----------JS구역--------------------------//
    console.log(props);

    //---------JSX구역Start--------------------------//

    return(<>
        <div>컴포넌트4가 전달한 속성 이름: {props.이름}/ 나이: {props.나이}</div>

     </>)
    //---------JSX구역End--------------------------//
}

export default 컴포넌트4;