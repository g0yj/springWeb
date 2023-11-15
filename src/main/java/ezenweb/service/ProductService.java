package ezenweb.service;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.dto.ProductDto;
import ezenweb.model.dto.ProductImgDto;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.entity.ProductEntity;
import ezenweb.model.entity.ProductImgEntity;
import ezenweb.model.repository.ProductCategoryRepository;
import ezenweb.model.repository.ProductImgRepository;
import ezenweb.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    ProductImgRepository productImgRepository;
    @Autowired
    FileService fileService;

//========================Category===========================================================//
    //1. 카테고리 등록
    @Transactional
    public boolean addCategory(ProductCategoryDto dto){
        //1. DTO->ENTITY
        //2. REPOSITROY를 이용한 엔티티 저장
        //3. 성공시 TRUE
        return productCategoryRepository.save(
                ProductCategoryEntity.builder().pcname(dto.getPcname()).build()).getPcno() >=1 ? true : false;

    }

    //2.카테고리 출력
    @Transactional
    public List<ProductCategoryDto> printCategory(){
        //1.모든엔티티호출 2.엔티티->dto 3.dto 리스트 반환
        return productCategoryRepository.findAll().stream().map(
                e ->{ return ProductCategoryDto.builder().pcno( e.getPcno() ).pcname( e.getPcname() ).build();  }
        ).collect(Collectors.toList());
    }

    //3. 카테고리 수정
    @Transactional
    public boolean updateCategory( ProductCategoryDto dto){
        //1. 수정할 엔티티 찾기 2. 찾은 엔티티가 존재하면 수정 3. 성공시 true  => 공통함수(5번) 사용
        ProductCategoryEntity productCategoryEntity=toEntity(dto.getPcno());
        if(productCategoryEntity!=null){productCategoryEntity.setPcname(dto.getPcname());return true;}
        return false;
    }

    //4. 카테고리 삭제
    @Transactional
    public boolean deleteCategory(int pcno){
        ProductCategoryEntity productCategoryEntity =toEntity(pcno);
        if(productCategoryEntity!=null){productCategoryRepository.delete(productCategoryEntity);return true;}
        return false;
    }

    //5. 부가적인 엔티티검색용 함수
    public ProductCategoryEntity toEntity(int pcno){
        Optional<ProductCategoryEntity> productCategoryEntityOptional=productCategoryRepository.findById(pcno);
        return productCategoryEntityOptional.isPresent()?productCategoryEntityOptional.get():null;
    }

//=================================Product=====================================================//
    //6. 제품등록
    @Transactional
    public boolean onProductAdd(ProductDto productDto){
        System.out.println("제품등록서비스도착 >"+productDto);
        //1.카테고리 entity
        ProductCategoryEntity productCategoryEntity=productCategoryRepository.findById(productDto.getPcno()).get();

        //2. 제품entity 생성
            //제품 엔티티에 카테고리 엔티티 넣기(단방향)
            // 제품 엔티티에 이미지 엔티티 리스트 넣기(양방향)
        //2-1 제품 엔티티 생성
        ProductEntity productEntity = ProductEntity.builder()
                //pno 문자로 선언 되어있음. uuid사용 중..
                .pno(productCategoryEntity.getPcno()+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .pname(productDto.getPname())
                .pcomment(productDto.getPcomment())
                .pprice(productDto.getPprice())
                .pstock(productDto.getPstock())
                .productCategoryEntity(productCategoryEntity)
                .productImgEntityList(new ArrayList<>()) //엔티티였나 .. @ 기본값 넣는거 안해줬었음. 그렇기 때문에 여기선 new 넣어서 해야됨.
                .build();

        //2-2 제품 이미지 등록 [첨부파일여러개]
        productDto.getFileList().stream().map( file->{
                    return fileService.fileUpload( file ); // 업로드 여러번
                }).collect(Collectors.toList()) // * 업로드된 식별파일명 반환되는데.. map 이니까 리스트로 반환
                .forEach( uuidFile ->{ //* 제품엔티티의 이미지리스트 에ㅌ이미지 엔티티 생성후 리스트에 등록
                    productEntity.getProductImgEntityList().add(
                            ProductImgEntity.builder()
                                    .uuidFileName( uuidFile ) .realFileName( uuidFile.split("_")[1])
                                    .productEntity( productEntity ).build()
                    );
                });
        //3. 제품등록
        productRepository.save(productEntity);
        return true;
    }

    //7. 제품출력
            // FileService.java를 거쳐 media 폴더 안에 등록된 이미지 저장됨.
    @Transactional
    public List<ProductDto> onProductAll(){
        System.out.println("출력 서비스");
        //1. 모든 제품 호출
        List <ProductEntity> productEntityList=productRepository.findAll(Sort.by(Sort.Direction.DESC,"cdate"));
                                                                        //Sort.Direction.DESC: 내림차순
        //2. 모든 제품의 entity->dto 변환하고 리스트로 반환
        return productEntityList.stream().map((p)->{
            return ProductDto.builder()
                    .pno(p.getPno())
                    .pname(p.getPname())
                    .pstock(p.getPstock())
                    .pprice(p.getPprice())
                    .pstate(p.getPstate())
                    .pcomment(p.getPcomment())
                    .categoryDto(ProductCategoryDto.builder()
                            .pcno(p.getProductCategoryEntity().getPcno())
                            .pcname(p.getProductCategoryEntity().getPcname())
                            .build())
                    .imgList(
                            p.getProductImgEntityList().stream().map((img)->{
                                return
                                        ProductImgDto.builder()
                                                .realFileName(img.getRealFileName())
                                                .uuidFileName(img.getUuidFileName())
                                                .build();

                                    }).collect(Collectors.toList())
                    )
                    .build();
        }).collect(Collectors.toList());

    }
    //8. 제품수정
    @Transactional
    public boolean onProductUpdate(ProductDto productDto){
        return false;
    }
    //9. 제품삭제
    @Transactional
    public  boolean onProductDelete(String pno){

        return false;
    }

//차트 데이터=============================================================================
    @Transactional
    public List<Map<Object,Object>> getBarChart(){
        System.out.println("바차트서비스실행");
        return productRepository.findByBarChart();
    }

    @Transactional
    public List<Map<Object,Object>> getPieChart(){
        return productRepository.findByPieChart();
    }




}//c
