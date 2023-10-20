import todo from './todo.js';
export default function todoList( props ){
    let response = [
            {tcontent : '리액트배우기' } ,
            {tcontent : '자바배우기' } ,
            {tcontent : '파이썬배우기' } ,
            {tcontent : 'C언어배우기' }
        ];

    return(<>
        <div className="todowrap">
            <h1> 나만의 할일 목록 </h1>
            <div className="todo_top">
                <input className="tcontent" type="text"/>
                <button onclick="onPost()" type="button"> 등록 </button>
            </div>




        <div className="CommentListBox">
            {
                response.map(r=>{
                    return( <todo tcontent={r.tcontent} />);
                })
            }


        </div>
    </>);
}