package net.javaApp.Ecommerce.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.payload.*;
import net.javaApp.Ecommerce.repository.CategoryRepository;
import net.javaApp.Ecommerce.service.InventoryService;
import net.javaApp.Ecommerce.service.impl.FilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
//    @GetMapping("/Products")
//    public ResponseEntity<?> getAllProducts(){
//        return ResponseEntity.ok(inventoryService.getAllProducts()) ;
//    }


    @PreAuthorize("hasRole('ROLE_SELLER')")
    //add product
    @PostMapping("/Product")
    public ResponseEntity<?> addProductToInventory(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = inventoryService.addProduct(productDto) ;
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED) ;
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    //add category
    @PostMapping("/Category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO category){
        Category addedCategory = inventoryService.addCategory(category.getCategory()) ;
        log.info("Added Category : {}", addedCategory.getName());

        return new ResponseEntity<>(new CategoryResponseDto("Added Category Successfully"), HttpStatus.CREATED) ;

    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
   // get products by product Id
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable(name = "productId") Long productId){
        Product product = inventoryService.getProductById(productId) ;
        return new ResponseEntity<>(product, HttpStatus.OK)  ;
    }

    //update product based on input parameters
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        log.info("Updating product info based on the input parameter received" , productUpdateRequestDto) ;
        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto = inventoryService.updateProduct(productUpdateRequestDto);

        return new ResponseEntity<>(updatedProductDto, HttpStatus.CREATED) ;
    }

    //delete product by id
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(
            @RequestParam(value = "productId", required = true) Long productId){
        inventoryService.deleteProduct(productId);
        return new ResponseEntity<>(new GenericResponseDto(true, "Product deleted successfully"), HttpStatus.ACCEPTED) ;
    }


//    public ResponseEntity<?> findProduct(){
//        Specification<Product> specification = new Specification<Product>() {
//            @Override
//            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return null;
//            }
//        } ;
//    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping("/productsSearch")
    public ResponseEntity<?> findProduct(@RequestBody SpecRequestDto requestDto){
       List<Product> products = inventoryService.findAllProducts(requestDto.getSearchRequestDto(), requestDto.getGlobalOperator()) ;
       return ResponseEntity.ok(products) ;
    }


}

