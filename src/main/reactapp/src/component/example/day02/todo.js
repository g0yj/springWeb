import styles from './todo.css'
export default function todo( props ){
    return(<>
        <div className="wrap"> {/*하나의 게시물 구역*/}
            <div>
                <img src={logo} className="pimg"/> {/*작성자 프로필*/}
            </div>
            <div className="commentBox">
                <div className="commentName">{props.name}</div> {/*작성자이름*/}
                <div className="commentContent">{props.content}</div> {/*작성자 게시물*/}
            </div>
        </div>

    </>);
}