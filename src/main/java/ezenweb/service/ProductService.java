package ezenweb.service;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.repository.ProductCategoryRepository;
import ezenweb.model.repository.ProductImgRepository;
import ezenweb.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
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

}//c
