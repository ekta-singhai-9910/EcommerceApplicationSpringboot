package net.javaApp.Ecommerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.payload.ProductUpdateDto;
import net.javaApp.Ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService inventoryService ;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(inventoryService.getAllProducts()) ;
    }


    @PostMapping("/addProduct")
    public ResponseEntity<?> addProductToInventory(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = inventoryService.addProduct(productDto) ;
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED) ;
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestParam(name = "category") String category){
        Category addedCategory = inventoryService.addCategory(category) ;
        log.info("Added Category : {}", addedCategory.getName());
        return new ResponseEntity<>("Added Category", HttpStatus.CREATED) ;

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable(name = "categoryId") long categoryId){
        return ResponseEntity.ok(inventoryService.getProductsByCategory(categoryId)) ;
    }


//    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateDto productUpdateDto){
//        return null ;
//    }

}
