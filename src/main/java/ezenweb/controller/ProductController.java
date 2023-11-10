package ezenweb.controller;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;


    @PostMapping("/category")
    public boolean addCategory(@RequestBody ProductCategoryDto dto){return productService.addCategory(dto);
    }

    @GetMapping("/category")
    public List<ProductCategoryDto> printCategory(){
        return productService.printCategory();
    }

    @PutMapping("/category")
    public boolean updateCategory(@RequestBody ProductCategoryDto dto){
        return productService.updateCategory(dto);
    }

    @DeleteMapping("/category")
    public boolean deleteCategory(@RequestParam int pcno){
        return productService.deleteCategory(pcno);

    }


}
