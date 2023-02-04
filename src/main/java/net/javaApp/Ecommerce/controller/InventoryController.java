package net.javaApp.Ecommerce.controller;

import net.javaApp.Ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService ;

    @GetMapping
    public ResponseEntity<?> getProductsByCategory(){
        return ResponseEntity.ok(inventoryService.getAllProducts()) ;
    }
}
