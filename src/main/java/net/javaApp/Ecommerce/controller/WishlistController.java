package net.javaApp.Ecommerce.controller;

import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.GenericResponseDto;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.payload.WishlistRequestDto;
import net.javaApp.Ecommerce.service.WishlistService;
import net.javaApp.Ecommerce.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService ;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/wishlist")
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistRequestDto requestDto){
       Long userId = CommonUtil.getUserIdFromToken() ;
       wishlistService.addToWishlist(requestDto, userId);
       return new ResponseEntity<>(new GenericResponseDto("Product added to the wishlist successfully"), HttpStatus.CREATED) ;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/wishlist")
    public ResponseEntity<?>  getWishlist(){
        Long userId = CommonUtil.getUserIdFromToken() ;
        List<ProductDto> productDtos = wishlistService.getWishlist(userId) ;
        return new ResponseEntity<>(productDtos, HttpStatus.OK) ;
    }

}
