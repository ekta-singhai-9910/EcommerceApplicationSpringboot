package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.model.Wishlist;
import net.javaApp.Ecommerce.payload.GenericResponseDto;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.payload.WishlistRequestDto;

import java.util.List;

public interface WishlistService {

    void addToWishlist(WishlistRequestDto productDto, long userId) ;

    List<ProductDto> getWishlist(Long userId) ;

}
