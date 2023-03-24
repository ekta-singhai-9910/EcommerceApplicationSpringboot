package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.payload.*;

import java.util.List;

public interface InventoryService {

 //   List<Product> getAllProducts() ;
    List<Product> getProductsByCategory(long categoryId, String categoryName) ;

    ProductDto addProduct(ProductDto productDto) ;

    Category addCategory(String category) ;

    ProductDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto) ;

    void deleteProduct(Long productId) ;

    public List<Product>findAllProducts(List<SearchRequestDto> requestDto, SpecRequestDto.GlobalOperator globalOperator) ;

}
