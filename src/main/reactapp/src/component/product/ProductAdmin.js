/*mui tab*/
import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';  //오류: lab 설치해줘야됨
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

import axios from 'axios';
import { BrowserRouter , Routes , Route , Link,useSearchParams,useParams } from 'react-router-dom'
import { useState, useEffect } from 'react'



import CategoryWrite from'./CategoryWrite';
import ProductWrite from'./ProductWrite';
import ProductList from'./ProductList';
import ProductInfo from'./ProductInfo';


export default function ProductAdmin( props ){

  //0. 출력할 카테고리 목록 (처음에 CategoryWrite에 만들었었는데 공통함수로 빼기 위해 이 파일로 이동시킴)
  const[categoryList,setCategoryList] =useState([])
  //2. 카테고리 출력[컴포넌트가 열렸을째, 등록에 성공했을때 , 삭제되었을때](여기서 뿐만 아니라productInfo.js에도 사용하고 싶어! 상위 컴포넌트로 이동시킴)
   const printCategory=(e)=>{
      axios.get('/product/category')
            .then((r)=>{
                console.log(r.data);
                setCategoryList(r.data)

            })
   }

    useEffect(()=>{printCategory()},[]); // 처음열렸을때 한번만 실행.


    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {setValue(newValue);};

    return(<>
        <h3>제품 관리페이지</h3>
        <Box sx={{ width: '100%', typography: 'body1' }}>
              <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>

                  <TabList
                    onChange={handleChange}
                    aria-label="lab API tabs example"
                    indicatorColor="secondary"
                    centered
                  >
                    <Tab label="카테고리등록" value="1" />
                    <Tab label="제품등록" value="2" />
                    <Tab label="제품목록" value="3" />
                    <Tab label="제품상태" value="4" />
                  </TabList>
                </Box>

                <TabPanel value="1"><CategoryWrite
                                          categoryList={categoryList}
                                          printCategory={printCategory}
                />
                </TabPanel>

                <TabPanel value="2"><ProductWrite
                                          categoryList={categoryList}
                                          printCategory={printCategory}
                />
                </TabPanel>
                <TabPanel value="3"><ProductList/></TabPanel>
                <TabPanel value="4"><ProductInfo/></TabPanel>
              </TabContext>
            </Box>
    </>)
    }

/*
    59번째줄 수정 전
                    <TabPanel value="1"><CategoryWrite/></TabPanel>
                    <TabPanel value="2"><ProductWrite/></TabPanel>
                    <TabPanel value="3"><ProductList/></TabPanel>
                    <TabPanel value="4"><ProductInfo/></TabPanel>


*/