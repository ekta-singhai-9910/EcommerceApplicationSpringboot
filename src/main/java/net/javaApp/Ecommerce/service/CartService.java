package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.payload.Cart.AddToCartDto;
import net.javaApp.Ecommerce.payload.Cart.CartItemResponseDto;

import java.util.List;

public interface CartService {

    AddToCartDto addToCart(AddToCartDto addToCartDto, Long userId) ;

    List<CartItemResponseDto> getAllCartItems(Long userId) ;
}
