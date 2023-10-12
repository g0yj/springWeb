console.log('phone js 실행');


//1.POST
function doPost(){

$.ajax({
         url : "/todo",
        method : "post",
        data : JSON.stringify({
                    tcontent:tcontent,
                    tstate:false
                    }),
        contentType: "application/json;charset=UTF-8",
        success : r=>{
        console.log('통신성공');console.log(r)
         doGet()//재출력
        } ,
        error : e=>{console.log('통신실패')} ,
   });
}




//2.GET
doGet()
function doGet(){
    $.ajax({
        url : "/todo",
        method : "get",
        data : {},
        success : r=>{console.log('통신성공');console.log(r)
                let html=``;
                console.log(html)
                r.forEach(t=>{
                 html+=
                          `  <div class="todo ${t.tstate ? 'successTodo':''} ">
                                <div class="tcontent">${t.tcontent} </div>
                                <div class="etcbtns">
                                    <button onclick="onPut(${t.tno},${t.tstate})" type="button">상태변경</button>
                                    <button onclick="onDelete(${t.tno})" type="button">제거하기</button>
                                </div>
                            </div>
                            `

                })

                 document.querySelector('.todo_bottom').innerHTML=html;
        } ,



        error : e=>{console.log('통신실패')} ,
   });

}


//3.PUT
function doPut(){
    $.ajax({
        url : "/todo",
        method : "put",
        data : JSON.stringify({
                                tno:tno,
                                tstate:!tstate
                                   }),
        contentType: "application/json;charset=UTF-8",
        success : r=>{
            console.log('통신성공');console.log(r)
            doGet()
             } ,


        error : e=>{console.log('통신실패')} ,
   });

}
//4.DELETE
function onDelete(){
    $.ajax({
        url : "/todo",
        method : "delete",
        data : {tno:tno},
        success : r=>{
            console.log('통신성공');console.log(r)
            doGet()
            } ,
        error : e=>{console.log('통신실패')} ,
   });

}

