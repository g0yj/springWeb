import{useState} from 'react'; //리액트 내장함수 중에 useState 훅 중에 하나의 함수

export default function 상태관리컴포넌트( props ){

    let value1=10;
    function value1증가(e){value1++;}

    //useState함수에 매개변수 전달하고 2개를 가지는 배열을 리턴함
        /*
            useState
                [0]: 값
                    - 지역변수가 아닌 전역변수로 관리됨(랜더링할 때 재선언안됨=>랜더링할때 상태[데이터]유지). 단, 다른 컴포넌트에서는 사용 불가능!!
                [1] : 그 값을 수정 할 수 있는 함수->bound dispatchSetState
                    -*해당 컴포넌트만 재실행[랜더링]  value2증가함수 누를때마다 재랜더링 alert뜸..!! value1버튼은 처음에 alert뜨고 나머지는 반응x

            let [ 변수명(초기값) , set함수명(수정함수) ] = useState( 초기값 )
                                            //초기값 : 데이터,객체,배열,함수
                                            //useState() : 반환값 배열!
        */
   let 상태함수= useState('훅이란무엇인가?')
    console.log(상태함수) //[0]훅이란무엇인가 [1]f()

    let [value2,setValue2]=useState(10); //useState는 초기값을 전역변수로 관리하기 때문에 함수가 끝나더라도 (value2증가()) 초기화 되지 않음(재선언안됨) 따라서 누적 가능함!
    function value2증가(e){setValue2(value2++)}

    alert('컴포넌트 랜더링중(함수재실행중..)')

    let value3 = '텍스트입력'

    let[value4,setValue4]=useState('텍스트입력')
    const value4변경=(e)=>{setValue4(e.target.value);}
                                //e.target : 해당 이벤트가 실행한 마크업/컴포넌트
                                //document.querySelector('input')와 동일한 의미!

    return(<>
            {/*
                리액트는 컴포넌트를 호출하는 형식임. return은 한번만 랜더링해서 반환됨.
                value1증가 버튼 눌러봤자 소용 없음!!!
                상태관리컴포넌트를 사용해 변화를 만들어보자. value2증가버튼이 예시
            */}

            <div>{value1}<button onClick={value1증가}>value1증가</button></div>
            <div>{value2}<button onClick={value2증가}>value2증가</button></div>
            {/*
              -setValue2(변경할값) : 변경할 값에 계산식이 있을 경우 ++가 뒤에 있으니까 랜더링 후에 적용되고 해서 한번 눌렀을 때 작동 안하고 두번 눌렀을 때 증가하는 걸 볼 수 있음.
              연산이 있을 경우에 유의하셈!
            */}


            <div><input type="text" /></div> {/*input 생기고 글자도 적힘*/}
            <div><input type="text" value={value3 }/></div>{/*input에 '텍스트입력' 들어가있고 수정 안됨!. 이미 출력됐고 useState()쓰지 않았기 때문에 재랜더링 안됨. */}
            <div><input type="text" value={value4 } onChange={value4변경}/></div>

        </>)
    }
