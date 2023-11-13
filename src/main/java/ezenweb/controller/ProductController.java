package ezenweb.controller;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.dto.ProductDto;
import ezenweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    //카테고리등록
    @PostMapping("/category")
    public boolean addCategory(@RequestBody ProductCategoryDto dto){return productService.addCategory(dto);}

    //카테고리출력
    @GetMapping("/category")
    public List<ProductCategoryDto> printCategory(){
        return productService.printCategory();
    }
    //카테고리수정
    @PutMapping("/category")
    public boolean updateCategory(@RequestBody ProductCategoryDto dto){
        return productService.updateCategory(dto);
    }
    //카테고리삭제
    @DeleteMapping("/category")
    public boolean deleteCategory(@RequestParam int pcno){
        return productService.deleteCategory(pcno);
    }


    //1. 제품등록
    @PostMapping("")
    public boolean onProductAdd(ProductDto productDto){
        System.out.println("컨트롤러도착"+productDto);
        return productService.onProductAdd(productDto);

    }
    //2. 제품출력
    @GetMapping
    public List<ProductDto> onProductAll(){
        System.out.println("출력컨트롤러");
        return productService.onProductAll();
    }
    //3. 제품수정
    @PutMapping
    public boolean onProductUpdate(@RequestBody ProductDto productDto){
        return false;
    }
    //4. 제품삭제
    @DeleteMapping
    public  boolean onProductDelete(@RequestParam String pno){
        return false;
    }




}//c
