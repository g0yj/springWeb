import axios from 'axios';
import { BrowserRouter , Routes , Route , Link,useSearchParams,useParams } from 'react-router-dom'
import { useState, useEffect } from 'react'

import Category from'./Category'
import ProductAdmin from'./ProductAdmin'

export default function CategoryWrite( props ){

let array=[1,2,3,4,5]

let copyArray=[]
/*
for(let i=0;i<array.length;i++){
    copyArray[i]=array[i];
}

for (let i in array){
    copyArray[i]=array[i]
}

for (let i of array){
    copyArray.push(i)
}
*/
array.forEach(i=>{})

console.log(copyArray)
//console.log(array)
//console.log(copyArray)




 {/*

  //0. 출력할 카테고리 목록 (여기서 뿐만 아니라productInfo.js에도 사용하고 싶어! 상위 컴포넌트로 이동시킴)
  const[categoryList,setCategoryList] =useState([])
 */}
   //1.카테고리 등록 [등록버튼 눌렀을 때]
   const addCategory=(e)=>{
      let info={pcname:document.querySelector('.pcname').value}
      console.log(info)
      axios.post('/product/category',info)
            .then((r)=>{
            console.log(r.data)
            if(r.data){
                alert('등록성공');
                props.printCategory();
            } else{alert('등록실패')}

            })

   }
 {/*
   //2. 카테고리 출력[컴포넌트가 열렸을째, 등록에 성공했을때 , 삭제되었을때](여기서 뿐만 아니라productInfo.js에도 사용하고 싶어! 상위 컴포넌트로 이동시킴)
   const printCategory=(e)=>{
      axios.get('/product/category')
            .then((r)=>{
                console.log(r.data);
                setCategoryList(r.data)

            })
   }

    useEffect(()=>{printCategory()},[]); // 처음열렸을때 한번만 실행.
 */}
   //3. 카테고리 수정[수정버튼 클릭했을때]
   const updateCategory=(e,pcno)=>{
      let info={pcno: pcno , pcname:"" }
      axios.put('/product/category',info)
            .then((r)=>{console.log(r.data)})

   }

   //4. 카테고리 삭제[삭제버튼 클릭했을때]
   const deleteCategory=(e,pcno)=>{
      axios.delete('/product/category',{params:{pcno:pcno}})  //controller에서 @param으로 받음
            .then((r)=>{console.log(r.data); props.printCategory();})

   }



    return(<>
        <div style={{width:'300px',margin:'0 auto'}}>
            <h3>카테고리등록</h3>
                <input type="text" className="pcname" placeholder='카테고리명'/>
                <button type="button" onClick={addCategory}>등록</button>



        <h3>카테고리 출력</h3>
        {
            props.categoryList.map((c)=>{
                return <Category
                            category={c}
                            deleteCategory={deleteCategory}
                        />
             })
        }

        </div>



    </>)
    }

/*
    69번째줄. js는 매개변수의 타입이 정해져있지 않기 때문에 함수 등 다양한 걸 모두 전달할 수 있음. 컴포넌트 사용이 가능한 이유!!
*/

/*
    카테고리 출력부분 . 공통함수 빼기전

            <h3>카테고리 출력</h3>
            {
                categoryList.map((c)=>{
                    return <Category
                                category={c}
                                deleteCategory={deleteCategory}
                            />
                 })
            }

            </div>



        </>)
        }

*/