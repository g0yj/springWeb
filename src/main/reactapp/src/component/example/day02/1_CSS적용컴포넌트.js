// 리액트 확장자 :jsx, js 둘 다 가능
/*
    1-1. css속성[카멜표기법 ex.background-color(x) backgroundColor(o)]이 정의된 객체 선언
    1-2. 마크업 style속성={css속성이있는객체}

    2-1 마크업 style속성={{속성명:속성값, 속성명:속성값}}

    3-1

*/
import styles from './컴포넌트.css';

function CSS컴포넌트( props ){

    //1. CSS를 객체의 속성[카멜표기법사용]으로 선언하기
    const cssStyle = {
        backgroundColor: 'red',
        width: '500px',
        height: '200px',
        margin:'0 auto'
    }

    return(<>
    <div style={cssStyle}>CSS 적용하는 방법1</div>

    {/*style속성에 {{속성명:속성값, 속성명:속성값}}*/}
    <div style={{
                        backgroundColor: 'blue',
                        width: '500px',
                        height: '200px',
                        margin:'0 auto'
                    }}>CSS 적용하는 방법2</div>


    <div className="box3">CSS 적용하는 방법3</div>

    </>);
}


export default CSS컴포넌트 ;
