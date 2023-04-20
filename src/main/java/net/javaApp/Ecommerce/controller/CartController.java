package net.javaApp.Ecommerce.controller;

import jakarta.validation.Valid;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.Cart.CartDto;
import net.javaApp.Ecommerce.payload.Cart.CartResponseDto;
import net.javaApp.Ecommerce.payload.GenericResponseDto;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.service.AuthService;
import net.javaApp.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService ;

    @Autowired
    private AuthService authService ;


    @Autowired
    private UserRepository userRepository ;



    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping
    ResponseEntity<?> addToCart(@Valid @RequestBody CartDto addToCartDto){
      Long userId = authService.getUserIdFromToken() ;
      CartDto addToCartDto1 = cartService.addToCart(addToCartDto, userId) ;
      CartDto cartItemResponseDto = new CartDto(addToCartDto1.getId(), addToCartDto1.getProductId(),
                addToCartDto1.getQuantity()) ;
        List<CartDto> list = new ArrayList<>() ; list.add(cartItemResponseDto) ;
      return new ResponseEntity<>(new CartResponseDto("Product added successfully to the cart",
              list), HttpStatus.CREATED) ;
    }


    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping
    ResponseEntity<?> getCartItems(){
        Long userId = authService.getUserIdFromToken() ;
        List<CartDto> responseDtoList = cartService.getAllCartItems(userId) ;
        return new ResponseEntity<>(new CartResponseDto("List of Cart Items fetched " +
                "successfully", responseDtoList), HttpStatus.OK) ;

    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PutMapping
    ResponseEntity<?> updateCartItem(@RequestBody  CartDto updateCartDto){
        Long userId = authService.getUserIdFromToken() ;
        CartDto updatedCartDto = cartService.updateCartItem(updateCartDto, userId) ;
        List<CartDto> list = new ArrayList<>() ; list.add(updatedCartDto) ;
        return new ResponseEntity<>(new CartResponseDto("Updated the cart " +
                "item", list), HttpStatus.CREATED) ;
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @DeleteMapping("/{cartItemId}")
    ResponseEntity<?> deleteCartItemByCartItemId(@PathVariable("cartItemId") Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(new GenericResponseDto(true, "Deleted the cart item "), HttpStatus.OK) ;
    }



}
