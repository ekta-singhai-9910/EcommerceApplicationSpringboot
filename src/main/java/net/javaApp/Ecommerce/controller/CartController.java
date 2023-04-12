package net.javaApp.Ecommerce.controller;

import jakarta.validation.Valid;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.Cart.AddToCartDto;
import net.javaApp.Ecommerce.payload.Cart.CartItemResponseDto;
import net.javaApp.Ecommerce.payload.Cart.CartResponseDto;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.service.CartService;
import net.javaApp.Ecommerce.utils.CommonUtil;
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
    private UserDetailsService userDetailsService ;

    @Autowired
    private UserRepository userRepository ;

    public long getUserIdFromToken(){
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName()) ;
        Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername()) ;
        long userId = user.get().getId();
        return userId ;
    }
   // @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartDto addToCartDto){
      Long userId = getUserIdFromToken() ;
      AddToCartDto addToCartDto1 = cartService.addToCart(addToCartDto, userId) ;
      CartItemResponseDto cartItemResponseDto = new CartItemResponseDto( addToCartDto1.getProductId(),
              addToCartDto1.getQuantity(), addToCartDto1.getId()) ;
        List<CartItemResponseDto> list = new ArrayList<>() ; list.add(cartItemResponseDto) ;
      return new ResponseEntity<>(new CartResponseDto("Product added successfully to the cart",
              list), HttpStatus.CREATED) ;
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    ResponseEntity<?> getCartItems(){
        Long userId = getUserIdFromToken() ;
        List<CartItemResponseDto> responseDtoList = cartService.getAllCartItems(userId) ;
        return new ResponseEntity<>(new CartResponseDto("List of Cart Items fetched " +
                "successfully", responseDtoList), HttpStatus.OK) ;

    }
}
