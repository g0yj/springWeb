import axios from 'axios';
import {useState, useEffect} from 'react'
export default function BoardList( props ){
    //0. 컴포넌트 상태변수 관리
    let [rows,setRows]=useState([]) //[] 결과를 리스트로 받을거기 때문에

    //1. axios를 이용한 스프링의 컨트롤러와 통신
    useEffect(()=>{
        axios.get('/board')
            .then(r=>{
                      console.log(r.data)
                      setRows(r.data); //응답 받은 모든 게시물을 상태변수에 저장
                        //setState : 해당 컴포넌트가 업데이트(새로고침,재랜더링/retrun )
                    })
    },[])

    return(<>
        <div>
            <h3>게시물목록</h3>
            <a href="/board/write">글쓰기</a>

            <table>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>

                {/*게시물 내용 반복*/}
                {
                    rows.map((row)=>{
                        return (<>
                            <tr>
                                <td>{row.bno}</td>
                                <td>{row.btitle}</td>
                                <td>{row.mno}</td>
                                <td>{row.cdate}</td>
                                <td>{row.bview}</td>
                            </tr>
                        </>)
                    })

                }


            </table>

        </div>

        </>)
    }
