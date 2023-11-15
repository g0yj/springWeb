import ProductAdmin from'./ProductAdmin'
import { BrowserRouter , Routes , Route , Link,useSearchParams,useParams } from 'react-router-dom'
import axios from 'axios';
import{useContext} from 'react'
import{SocketContext} from '../Index.js'

export default function ProductWrite( props ){

    //1. 상위컴포넌트에 있는 context의 들어있는 클라이언소켓 꺼내기
    const clientSocket=useContext(SocketContext).current;

    //1. 제품등록
    const onProductAdd=(e)=>{
        console.log('등록버튼실행');
        let productForm =document.querySelectorAll('.productForm')[0];
        let productFormData=new FormData(productForm);
        axios.post('/product',productFormData)
            .then(r=>{
                if(r.data){
                //[소켓사용 10번째 줄 있어야 가능]: 서버에게 메시지 보내기
                clientSocket.send('새로운 제품이 등록되었습니다');
                productForm.reset();
                }else{alert('제품등록실패');}

            })


    }

    return(<>
    <div style={{width:'300px',margin:'0 auto'}}>
        <h3>제품 등록</h3>
        <form className='productForm'>
            <select name='pcno'>
                {
                props.categoryList.map((c)=>{
                    return (<>
                        <option value={c.pcno}>{c.pcname}</option>
                    </>)
                })

            }
          </select>
          <input type="text" name="pname" placeholder="제품명"/> <br/>
          <textarea type="text" name="pcomment" placeholder="제품설명"></textarea> <br/>
          <input type="text" name="pprice" placeholder="제품가격"/> <br/>
          <input type="text" name="pstock" placeholder="초기재고"/> <br/>
          <input type="file" name="fileList" multiple/> <br/>
          <button type="button" onClick={onProductAdd}> 등록</button>

        </form>
    </div>


     </>)
    }
