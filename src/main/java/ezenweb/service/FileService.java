package ezenweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

/*
 파일 관련 메소드 정의


*/
@Service
public class FileService {
    //0. 파일경로 [배포전]
    private  String fileRootPath="C:\\java\\";
    //1. 업로드
    public String fileUpload(MultipartFile multipartFile) {
        //0. 유효성검사
        if (multipartFile.isEmpty()) {//파일이 비어있으면
            return null;
        }//i
        //1. 파일명[파일명이 동일한 이름일 수 있기 때문에 파일명은 식별자가 될 수 없음!! 식별자를 만들어야됨]
                //해결방법: 1. UUID 조합 2.날짜/시간 조합 3. 상위컨텐츠PK등등
        String fileName=
                UUID.randomUUID().toString()+"_"+
                multipartFile.getOriginalFilename().replaceAll("_","-"); //만일 식별을 위해 _를 제거하고 -로 변경
        //2. 파일경로
        File file=new File(fileRootPath+fileName);
        //3. 업로드
        try {
            multipartFile.transferTo(file);
            return fileName; //성공 시 파일명 리턴[db에 저장을 위해]
        }catch (Exception e){
            System.out.println("업로드실패"+e);
            return null;
        }
    }//m

//-------------------------------------------------------------------------------------------------------------//
    //2. 다운로드
    @Autowired private HttpServletResponse response;
    public void fileDownload(String uuidFile){
        //1. 다운로드 할 파일경로 찾기
        String downloadFilePath=fileRootPath+uuidFile;
        //2.uuid 제거된 순수 파일명[다운로드 시 출력되는 파일명이니까 uuid제거]
        String fileName= uuidFile.split("_")[1]; //_기준으로 쪼갠 후 뒷자리 파일명만 호출
        //3.다운로드 형식 구성 [브라우저가 지원하는 다운로드 형식으로 별도로 커스텀 불가능]
        try{
            response.setHeader("Content-Disposition","attachement;filename=" + URLEncoder.encode(fileName,"utf-8"));
            //4. 다운로드
            // ----------------------------[다운로드]서버가 해당 파일 읽어오기 ----------------------------------------------//
                //4-1 서버가 해당 파일 읽어오기
            File file = new File(downloadFilePath);
                //4-2 버퍼스트림 이용한 바이트로 파일 읽어오기
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
                //4-3 파일의 용량[바이트]만큼 바이트배열 선언
            byte[]bytes = new byte[(int)file.length()];
                //4-4 버퍼스트림이 읽어온 바이트들을 바이트배열에 저장
            fin.read(bytes);
            // ----------------------------[다운로드]서버가 읽어온 파일을 클라이언트에게 응답 ----------------------------------------------//
                //4-5 버퍼스트림 이용한 response으로 응답
            BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());
                //4-6 읽어온 바이트[파일] 내보내기
            fout.write(bytes);
                //4-7 안전하게 스트림 닫기
            fout.flush(); fout.close();fin.close();
        }catch (Exception e){}


    }


}//c
