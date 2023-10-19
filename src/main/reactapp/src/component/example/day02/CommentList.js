//컴포넌트를 호출하기 :import 컴포넌트명 from '파일경로'
import Comment from './Comment.js';
export default function CommentList( props ){
    //예) 만약에 ajax가 response한 데이터가 있다면?!
    let response = [
            {name : '유재석' , content : '안녕하세요1'} ,
            {name : '강호동' , content : '안녕하세요2'} ,
            {name : '신동엽' , content : '안녕하세요3'}
        ];

    return(<>
        <div className="CommentListBox">
            {
                response.map(r=>{
                    return( <Comment name={r.name} content={r.content}/>);
                })
            }


        </div>
    </>);
}