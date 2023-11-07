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
//--------pagination-----------------------//
import Pagination from '@mui/material/Pagination';

export default function BoardList( props ){
    //0. 컴포넌트 상태변수 관리
    // 페이징 처리 시 새로운 DTO로 묶지 않고 전달한 경우
    //let [rows,setRows]=useState([]) //[] 결과를 리스트로 받을거기 때문에
    // 페이징 처리 후 전달(스프링으로부터 전달 받는 객체)
    let[pageDto,setPageDto] = useState({
        boardDtos:[],
        totalPages:0,
        totalCount:0
    }); //스프링에서 전달해주는 DTO와 일치 시켜주면 보기 편함.

     // 페이징 처리만 했을 때,
    //const[page,setPage]=useState(1);//페이지 번호 사용
    const[pageInfo,setPageInfo]=useState({
        page:1, key:'',keyword:''
    }) ;console.log(pageInfo);


    //1. axios를 이용한 스프링의 컨트롤러와 통신[실행조건: 컴포넌트 실행됐을 때, 페이지가 바꾸ㅕㅆ을때]
    useEffect(()=>{
        axios.get('/board',{params:pageInfo })
            .then(r=>{
                      console.log(r.data)
                     // setRows(r.data); // 페이징처리 dto 묶기 전. 응답 받은 모든 게시물을 상태변수에 저장
                        //setState : 해당 컴포넌트가 업데이트(새로고침,재랜더링/retrun )
                      console.log('입력받은페이지'+pageInfo);
                      console.log(r.data);
                      setPageDto(r.data); //19번째줄 참고
                    })
    },[pageInfo])//페이지가 변경될 때.[의존성배열]

    //페이징처리 (스프링에게 전달할 객체) - 페이지 번호 클릭했을 때,
    const onChangeSelect=(e,value)=>{
        console.log(e);console.log(value);
        //페이징 처리만 했을때
        //setPage(value);
        //검색기능까지 묶어서
        pageInfo.page =value; //클릭한 페이지번호로 변경
        setPageInfo({...pageInfo}); //새로고침[상태변수의 주소값이 바뀌면 재랜더링]
        }

    //검색 버튼을 눌렀을때 -
    const onSearch =(e)=>{}



   return (
    <>
     <h3>게시물목록</h3>
     <a href="/board/write">글쓰기</a>
    <p>현재 페이지{pageInfo.page}</p>
     <TableContainer component={Paper}>
       <Table sx={{ minWidth: 650 }} aria-label="simple table">
         <TableHead>
           <TableRow>
             <TableCell align="right">게시물번호</TableCell>
             <TableCell align="right">제목</TableCell>
             <TableCell align="right">작성자</TableCell>
             <TableCell align="right">작성일(</TableCell>
             <TableCell align="right">조회수</TableCell>
           </TableRow>
         </TableHead>
         <TableBody>
           {pageDto.boardDtos.map((row) => (
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
     <div style={{display:'flex',flexDirection: 'column', alignItems:'center', margin:'10px'}}>
     <Pagination count={pageDto.totalPages} onChange={onChangeSelect}/>
     {/*검색기능*/}
     <div>
        <select
            value={pageInfo.key}
            onChange={(e)=>{setPageInfo({...pageInfo,key:e.target.value})}}
        >
            <option value="btitle">제목</option>
            <option value="bcontent" >내용</option>
        </select>
        <input
            value={pageInfo.keyword}
            onChange={(e)=>{setPageInfo({...pageInfo,keyword:e.target.value})}}
            type="text"
            />
        <button onClick={onSearch} type="button">검색</button>
     </div>

     </div>

</>)
    }

/*
    mui : 리액트 전용 라이브러리  참고:https://mui.com/material-ui/getting-started/installation/
    1. 설치
    기본설치": npm install @mui/material @emotion/react @emotion/styled
    스타일 구성 요소 사용 : npm install @mui/material @mui/styled-engine-sc styled-components


*/