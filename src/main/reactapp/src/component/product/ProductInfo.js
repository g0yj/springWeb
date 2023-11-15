import { BarChart } from '@mui/x-charts/BarChart';
import { PieChart } from '@mui/x-charts/PieChart';
import axios from 'axios';
import{useState,useEffect,useRef,useContext,createContext} from 'react'

export default function ProductInfo( props ){
    //1.막대차트에 필요한 데이터 요청[실행조건: 컴포넌트 열릴때]
    const [barChartData,setBarChartData] = useState([]);
    const getBarChart=(e)=>{
        axios.get("/product/barchart")
            .then(r=>{
                console.log(r.data)
                setBarChartData(r.data)
                })

    }
    useEffect(()=>{getBarChart()},[])

    //2. 원형차트에 필요한 데이터 요청[실행조건: 컴포넌트 열릴때]
    const [pieChartData,setPieChartData] = useState([]);
    const getPieChart=(e)=>{
        axios.get("/product/piechart")
            .then(r=>{
                console.log(r.data)
                setPieChartData(r.data)
            })
    }
    useEffect(()=>{getPieChart()},[])

    return(<>
        <div style={{display:'flex'}}>
            <div>
                <h3>제품별 재고 수</h3>

                {/*barChartData에 데이터가 있을때만 차트 표시. 데이터개수가 0이 아니면 차트 표시하고 아니면 안함. 삼항연산자 사용.
                   왜 조건을 달아주는가?
                */}
                {
                    barChartData.length!=0?
                    <BarChart
                      xAxis={[
                        {
                          id: 'barCategories',
                          data: barChartData.map((p)=>{return p.pname}),
                          scaleType: 'band',
                        },
                      ]}
                      series={[
                        {
                          data: barChartData.map((p)=>{return p.pstock}),
                        },
                      ]}
                      width={500}
                      height={300}
                    />
                    :<></>
                }
            </div>
            <div>
                <h3>카테고리별 제품 수</h3>
                <PieChart
                  series={[
                    {
                      data: pieChartData.map((p,i)=>{return {id:i,value:p.count,label:p.pcname}}) ,
                    },
                  ]}
                  width={400}
                  height={200}
                />
            </div>
        </div>
       </>)
    }
