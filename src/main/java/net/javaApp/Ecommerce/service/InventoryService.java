package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.model.Product;

import java.util.List;

public interface InventoryService {

    List<Product> getAllProducts() ;
    List<Product> getProductsByCategory(long categoryId) ;
}
