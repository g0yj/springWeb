import{useState} from 'react'; //리액트 내장함수 중에 useState 훅 중에 하나의 함수

export default function 상태관리컴포넌트( props ){

    let value1=10;
    function value1증가(e){value1++;}

    //useState함수에 매개변수 전달하고 2개를 가지는 배열을 리턴함
        /*
            useState
                [0]: 값
                    - 지역변수가 아닌 전역변수로 관리됨(랜더링할 때 재선언안됨=>랜더링할때 상태[데이터]유지). 단, 다른 컴포넌트에서는 사용 불가능!!
                [1] : 그 값을 수정 할 수 있는 함수.bound dispatchSetState
                    -*해당 컴포넌트만 재실행[랜더링]  value2증가함수 누를때마다 재랜더링 alert뜸..!! value1버튼은 처음에 alert뜨고 나머지는 반응x
                    -setValue2(변경할값) :
            let [ 변수명(초기값) , set함수명(수정함수) ] = useState( 초기값 )
                                            //초기값 : 데이터,객체,배열,함수
                                            //useState() : 반환값 배열!
        */
   let 상태함수= useState('훅이란무엇인가?')
    console.log(상태함수) //[0]훅이란무엇인가 [1]f()

    let [value2,setValue2]=useState(10);
    function value2증가(e){setValue2(value2++)}

    alert('컴포넌트 랜더링중(함수재실행중..)')

    let value3 = '텍스트입력'

    let[value4,setValue4]=useState('텍스트입력')
    const value4변경=(e)=>{setValue4(e.target.value);}
                                //e.target : 해당 이벤트가 실행한 마크업/컴포넌트
                                //document.querySelector('input')와 동일한 의미!

    return(<>
            {/**/}
            <div>{value1}<button onClick={value1증가}>value1증가</button></div>
            <div>{value2}<button onClick={value2증가}>value2증가</button></div>

            <div><input type="text" /></div>
            <div><input type="text" value={value3 }/></div>
            <div><input type="text" value={value4 } onChange={value4변경}/></div>

        </>)
    }
