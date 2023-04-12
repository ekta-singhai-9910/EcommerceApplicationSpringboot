package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Cart;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.Cart.AddToCartDto;
import net.javaApp.Ecommerce.payload.Cart.CartItemResponseDto;
import net.javaApp.Ecommerce.repository.CartRepository;
import net.javaApp.Ecommerce.repository.ProductRepository;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository ;

    @Override
    public AddToCartDto addToCart(AddToCartDto addToCartDto, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId)
        ));
        Optional<Product> product = Optional.ofNullable(productRepository.
                findById(addToCartDto.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Product Id", addToCartDto.getProductId())
        ));
        Cart cart = new Cart(user.get(), product.get(), addToCartDto.getQuantity(), new Date()) ;
        Cart savedCart = cartRepository.save(cart) ;

        AddToCartDto addToCartDto1 = new AddToCartDto(savedCart.getId(),
                savedCart.getProduct().getId(), savedCart.getQuantity());
        return addToCartDto1 ;
    }

    @Override
    public List<CartItemResponseDto> getAllCartItems(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new EcommAPIException(HttpStatus.NOT_FOUND,
                        "User not registered. Register user to see cart")
        ));

        Optional<List<Cart>>cartList = Optional.ofNullable(cartRepository.
                findAllByUserOrderByCreatedDateDesc(user.get()).
                orElseThrow(() -> new EcommAPIException(HttpStatus.NOT_FOUND, "Your cart is empty")));

        List<CartItemResponseDto> list = new ArrayList<>() ;
        for(Cart cart: cartList.get()){
            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(cart.getProduct().
                    getId(), cart.getQuantity(), cart.getId()) ;
            list.add(cartItemResponseDto) ;
        }
        return list ;
    }
}
