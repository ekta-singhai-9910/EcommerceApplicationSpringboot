package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.CategoryDTO;
import net.javaApp.Ecommerce.payload.CategoryResponseDto;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.payload.ProductUpdateRequestDto;
import net.javaApp.Ecommerce.repository.CategoryRepository;
import net.javaApp.Ecommerce.repository.ProductRepository;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private CategoryRepository categoryRepository ;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll() ;
        return products ;
    }

    @Override
    public List<Product> getProductsByCategory(long categoryId, String categoryName) {

        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) throws UsernameNotFoundException {
        Product product = new Product() ;
        Optional<User> user = userRepository.findByUsernameOrEmail(productDto.getUsernameOrEmail(), productDto.getUsernameOrEmail()) ;
        Category category = categoryRepository.findByName(productDto.getCategory()) ;
        if(category == null){
            category = categoryRepository.save(new Category(productDto.getCategory()) ) ;
        }
        product.setId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(product.getQuantity());
        if( user == null){
            throw new UsernameNotFoundException( productDto.getUsernameOrEmail()) ;
        }
        product.setSeller(user.get());

        product.setCategory(category);
        Product addedProduct = productRepository.save(product);
        ProductDto addedProductDto = new ProductDto() ;
        addedProductDto.setCategory(addedProduct.getName());
        addedProductDto.setQuantity(addedProduct.getQuantity());
        addedProductDto.setPrice(addedProduct.getPrice());
        addedProductDto.setUsernameOrEmail(addedProduct.getSeller().getUsername());
        addedProductDto.setName(addedProduct.getName());
        return addedProductDto;
    }

    @Override
    public Category addCategory(String category) {
        Category c1 =  categoryRepository.findByName(category) ;
        if( c1 != null){
            throw new EcommAPIException( HttpStatus.BAD_REQUEST, "Category already exists") ;
        }
       Category newCategory = new Category();
       newCategory.setName(category);
       Category addedCategory = categoryRepository.save(newCategory) ;
       return addedCategory;
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(productUpdateRequestDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productUpdateRequestDto.getId())
        ));

        Product product1 = product.get();
        product1.setQuantity(productUpdateRequestDto.getQuantity() == -1L ?
                product1.getQuantity() : productUpdateRequestDto.getQuantity());
        product1.setPrice(productUpdateRequestDto.getPrice() == -1 ?
                product1.getPrice() : productUpdateRequestDto.getPrice());
        product1.setName(productUpdateRequestDto.getName() == null ?
                product1.getName() : productUpdateRequestDto.getName());
        if( productUpdateRequestDto.getCategory() != null){
           Category c =  categoryRepository.findByName(productUpdateRequestDto.getCategory()) ;
           if( c == null){
               c = new Category(productUpdateRequestDto.getCategory()) ;
               categoryRepository.save(c) ;
           }
        }

        product1.setCategory(productUpdateRequestDto.getCategory() == null ?
                product1.getCategory() :
                categoryRepository.findByName(productUpdateRequestDto.getCategory()));

        Product updatedProduct = productRepository.save(product1) ;
        ProductDto updatedProductDto = new ProductDto(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getSeller().getUsername(),
                updatedProduct.getCategory().getName(),
                updatedProduct.getQuantity()
        ) ;
       return updatedProductDto;

    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


}
