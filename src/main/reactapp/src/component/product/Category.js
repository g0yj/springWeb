import axios from 'axios'
import { useState, useEffect } from 'react'


export default function Category( props ){
    console.log('Category 실행')
    console.log(props)
    //props: 속성객체

    const category=props.category;

    return(<>
        <div style={{display:'flex', justifyContent:'space-between'}}>
            <div>{category.pcname}</div>
            <div>
                <button type="button">수정</button>
                <button onClick={(e)=>{props.deleteCategory(e,category.pcno)}} type="button">삭제</button>
            </div>
        </div>

    </>)

  }
