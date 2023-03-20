package net.javaApp.Ecommerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.payload.*;
import net.javaApp.Ecommerce.repository.CategoryRepository;
import net.javaApp.Ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService inventoryService ;

    @Autowired
    private CategoryRepository categoryRepository;

    //get all products
    @GetMapping("/Products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(inventoryService.getAllProducts()) ;
    }


    //add product
    @PostMapping("/Product")
    public ResponseEntity<?> addProductToInventory(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = inventoryService.addProduct(productDto) ;
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED) ;
    }

    //add category
    @PostMapping("/Category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO category){
        Category addedCategory = inventoryService.addCategory(category.getCategory()) ;
        log.info("Added Category : {}", addedCategory.getName());

        return new ResponseEntity<>(new CategoryResponseDto("Added Category Successfully"), HttpStatus.CREATED) ;

    }

    //get products by category
    @GetMapping("/products/category")
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam(value = "categoryId", required = false) long categoryId,
            @RequestParam(value = "categoryName", required = false) String categoryName
    ){
        List<Product> products = new ArrayList<>() ;
        if(Objects.equals(categoryId, null) && categoryName == null){
           products = inventoryService.getAllProducts() ;
        }
        else if( Objects.nonNull(categoryId) && categoryName != null ){
             Category c1 = categoryRepository.findByName(categoryName) ;
             Optional<Category> c2 = categoryRepository.findById(categoryId) ;
             if( c1 != c2.get()){
                 throw new EcommAPIException(HttpStatus.BAD_REQUEST, "category Id & category name do not match") ;
             }
             else inventoryService.getProductsByCategory(categoryId, categoryName);
        }
        else if(categoryName != null){
            Category c1 = categoryRepository.findByName(categoryName) ;
            categoryId = c1.getId();
        }
        try {
         products = inventoryService.getProductsByCategory(categoryId, categoryName);
        }catch( Exception ex){
            throw new ResourceNotFoundException("Products", "Category ID", categoryId) ;
        }
        return new ResponseEntity<>(products, HttpStatus.OK)  ;
    }

    //update product based on input parameters
    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        log.info("Updating product info based on the input parameter received" , productUpdateRequestDto) ;
        ProductDto updatedProductDto = new ProductDto();
        try {
            updatedProductDto = inventoryService.updateProduct(productUpdateRequestDto);
        }
        catch(Exception ex){
            throw new EcommAPIException( HttpStatus.BAD_REQUEST, "Input paramters not valid" ) ;
        }
        return new ResponseEntity<>(updatedProductDto, HttpStatus.CREATED) ;
    }

    //delete product by id
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(
            @RequestParam(value = "productId", required = true) Long productId){
        inventoryService.deleteProduct(productId);
        return new ResponseEntity<>(new GenericResponseDto("Prodduct deleted successfully"), HttpStatus.ACCEPTED) ;
    }


}

