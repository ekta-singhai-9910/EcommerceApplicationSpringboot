package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.repository.ProductRepository;
import net.javaApp.Ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository ;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll() ;
        return products ;
    }

    @Override
    public List<Product> getProductsByCategory(long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId) ;
        return products;
    }
}
