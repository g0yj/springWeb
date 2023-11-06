import axios from 'axios';
import {useState, useEffect} from 'react'
import { BrowserRouter , Routes , Route , Link } from 'react-router-dom'
//--------------mui table-----------------------//
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


export default function BoardList( props ){
    //0. 컴포넌트 상태변수 관리
    let [rows,setRows]=useState([]) //[] 결과를 리스트로 받을거기 때문에

    //1. axios를 이용한 스프링의 컨트롤러와 통신
    useEffect(()=>{
        axios.get('/board')
            .then(r=>{
                      console.log('게시물출력')
                      console.log(r.data)
                      setRows(r.data); //응답 받은 모든 게시물을 상태변수에 저장
                        //setState : 해당 컴포넌트가 업데이트(새로고침,재랜더링/retrun )
                    })
    },[])

   return (
    <>
     <h3>게시물목록</h3>
     <a href="/board/write">글쓰기</a>

     <TableContainer component={Paper}>
       <Table sx={{ minWidth: 650 }} aria-label="simple table">
         <TableHead>
           <TableRow>
             <TableCell align="right">게시물번호</TableCell>
             <TableCell align="right">제목</TableCell>
             <TableCell align="right">작성자</TableCell>
             <TableCell align="right">작성일(</TableCell>
             <TableCell align="right">조회수(g)</TableCell>
           </TableRow>
         </TableHead>
         <TableBody>
           {rows.map((row) => (
             <TableRow
               key={row.name}
               sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
             >

               <TableCell align="right">{row.bno}</TableCell>
               <TableCell align="right">
                <Link to= {"/board/view?bno="+row.bno}>{row.btitle}</Link>
                </TableCell>

               <TableCell align="right">{row.mno}</TableCell>
               <TableCell align="right">{row.cdate}</TableCell>
               <TableCell align="right">{row.bview}</TableCell>
             </TableRow>
           ))}
         </TableBody>
       </Table>
     </TableContainer>

</>)
    }

/*
    mui : 리액트 전용 라이브러리  참고:https://mui.com/material-ui/getting-started/installation/
    1. 설치
    기본설치": npm install @mui/material @emotion/react @emotion/styled
    스타일 구성 요소 사용 : npm install @mui/material @mui/styled-engine-sc styled-components


*/