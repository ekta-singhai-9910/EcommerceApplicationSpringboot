package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.payload.Cart.CartDto;
import net.javaApp.Ecommerce.payload.Cart.CartResponseDto;

import java.util.List;

public interface CartService {

    CartDto addToCart(CartDto addToCartDto, Long userId) ;

    List<CartDto> getAllCartItems(Long userId) ;

    CartDto updateCartItem(CartDto cartDto, Long userId) ;

    void deleteCartItem(Long id) ;
}
