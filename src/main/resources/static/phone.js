console.log('phone js 실행');



//1.POST
function doPost(){
let name=document.querySelector('.name').value;
let phone=document.querySelector('.phone').value;
$.ajax({
         url : "/phone",
        method : "post",
        data : JSON.stringify({
                    name:name,
                    number:phone
                    }),
        contentType: "application/json;charset=UTF-8",
        success : r=>{
        console.log('post통신성공');console.log(r)

         doGet()//재출력
        } ,
        error : e=>{console.log('post통신실패')} ,
   });
}


//2.GET
doGet()
function doGet(){
    $.ajax({
        url : "/phone",
        method : "get",
        data : {},
        success : r=>{console.log('get 통신성공');console.log(r)
                let html=``;
                console.log(html)
                r.forEach(t=>{
                 html+=
                          `         <div class="pno">
                                        <span class="pname">${t.name}</span>
                                        <span class="pphone">${t.number}</span>
                                        <button onclick="doPut(${t.pno})" type="button">수정</button>
                                        <button onclick="onDelete(${t.pno})" type="button">삭제</button>
                                    </div>

                            `

                })

                 document.querySelector('.print').innerHTML=html;
        } ,



        error : e=>{console.log('get 통신실패')} ,
   });

}





//3.PUT
function doPut(pno){
 let nname=prompt('수정할 이름');
 let nphone=prompt('수정할 번호');
    $.ajax({
        url : "/phone",
        method : "put",
        data : JSON.stringify({
                                name:nname,
                                number:nphone,
                                pno:pno

                                   }),
        contentType: "application/json;charset=UTF-8",
        success : r=>{
            console.log('put 통신성공');console.log(r)
            doGet()
             },


        error : e=>{console.log('put통신실패')},


    });
   }





//4.DELETE
function onDelete(pno){
    $.ajax({
        url : "/phone",
        method : "delete",
        data : {pno:pno},
        success : r=>{
            console.log('delete 통신성공');console.log(r)
            doGet()
            } ,
        error : e=>{console.log('delete통신실패')} ,
   });

}

