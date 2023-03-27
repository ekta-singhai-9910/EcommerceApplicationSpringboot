package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.Wishlist;
import net.javaApp.Ecommerce.payload.GenericResponseDto;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.payload.WishlistRequestDto;
import net.javaApp.Ecommerce.repository.ProductRepository;
import net.javaApp.Ecommerce.repository.WishlistRepository;
import net.javaApp.Ecommerce.service.WishlistService;
import net.javaApp.Ecommerce.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository ;
    @Autowired
    private  ProductRepository productRepository;

    @Override
    public void addToWishlist(WishlistRequestDto productDto, long userId) {
        Wishlist wishlist = new Wishlist() ;
        Optional<Product> product = Optional.ofNullable(productRepository.findById(productDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productDto.getProductId())));
        wishlist.setProduct(product.get());
        wishlist.setUserId(userId);
        wishlist.setCreatedDate(new Date());
        wishlistRepository.save(wishlist) ;
    }

    @Override
    public List<ProductDto> getWishlist(Long userId) {
        List<Wishlist> wishlistList = wishlistRepository.findAllByUserIdOrderByCreatedDateDesc(userId) ;
        List<ProductDto> productDtos = new ArrayList<>() ;
        for( Wishlist wishlist : wishlistList){
            ProductDto productDto = CommonUtil.getProductDtoFromProduct(wishlist.getProduct()) ;
            productDtos.add(productDto) ;
        }
        return productDtos;
    }
}
