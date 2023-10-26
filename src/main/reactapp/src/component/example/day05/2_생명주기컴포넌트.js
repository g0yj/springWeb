//231026  11시50분
/*
    컴포넌트의 생명주기[Life Cycle]
        탄생[Mounting]--------> 업데이트[updating] -------->제거[Unmpunting]
        1.함수생성
            |
            |
        2.함수랜더링


*/



import{useState,useEffect} from 'react'
export default function 생명주기컴포넌트( props ){
    //1. useState함수를 이용한 [변수, 수정함수] 하나를 리턴 받음.
    let [value,setValue]=useState(0);
    const valueUpdate=(e)=>{value++;setValue(value);}

    let [value2,setValue2]=useState(0);
    const value2Update=(e)=>{value2++;setValue2(value2)}

    //2. 컴포넌트 생명주기 1.탄생 2.업데이트 3.제거할때 실행되는 함수
        //1.컴포넌트 탄생때는 실행됨./ 업데이트할때도 실행됨
    useEffect(()=>{console.log('[1]Effect 실행')})
        //2. 컴포넌트 탄생(첫실행될)때 실행됨 / 업데이트할땐 실행 안됨!!
    useEffect(()=>{console.log('[2]Effect 실행')},[])
        //3. 컴포넌트 탄생시 실행/ 특정 상태 업데이트(value가 바뀔때!)
        //useEffect(()=>{함수,[의존성배열]})
    useEffect(()=>{console.log('[3]Effect 실행')},[value]) //실행 시 value은 변경되나 value2는 변경 안됨.
    //useEffect(()=>{console.log('[3]Effect 실행'),[value,value2]}) // value,value2 둘다 변경

    return(<>
        <div>{value}</div>
        <button onClick={valueUpdate}>+</button>

        <div>{value2}</div>
        <button onClick={value2Update}>+</button>
        </>)
    }
