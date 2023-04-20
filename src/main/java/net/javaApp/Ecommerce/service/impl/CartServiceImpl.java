package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Cart;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.Cart.CartDto;
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
    public CartDto addToCart(CartDto addToCartDto, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId)
        ));
        Optional<Product> product = Optional.ofNullable(productRepository.
                findById(addToCartDto.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Product Id", addToCartDto.getProductId())
        ));
        Cart cart = new Cart(user.get(), product.get(), addToCartDto.getQuantity(), new Date()) ;
        Cart savedCart = cartRepository.save(cart) ;

        CartDto addToCartDto1 = new CartDto(savedCart.getId(),
                savedCart.getProduct().getId(), savedCart.getQuantity());
        return addToCartDto1 ;
    }

    @Override
    public List<CartDto> getAllCartItems(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new EcommAPIException(HttpStatus.NOT_FOUND,
                        "User not registered. Register user to see cart")
        ));

        Optional<List<Cart>>cartList = Optional.ofNullable(cartRepository.
                findAllByUserOrderByCreatedDateDesc(user.get()).
                orElseThrow(() -> new EcommAPIException(HttpStatus.NOT_FOUND, "Your cart is empty")));

        List<CartDto> list = new ArrayList<>() ;
        for(Cart cart: cartList.get()){
            CartDto cartItemResponseDto = new CartDto(cart.getProduct().
                    getId(),  cart.getId(), cart.getQuantity()) ;
            list.add(cartItemResponseDto) ;
        }
        return list ;
    }

    @Override
    public CartDto updateCartItem(CartDto cartDto, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new EcommAPIException(HttpStatus.NOT_FOUND,
                        "User not registered. Register user to see cart")
                ));

        Optional<Product>product = Optional.ofNullable(productRepository.findById(cartDto.getProductId()).orElseThrow(
                () -> new EcommAPIException(HttpStatus.NOT_FOUND,
                "Product does not exist in the cart")
        )) ;
        Optional<Cart> cart = Optional.of(cartRepository.findByUserAndProduct(user.get(), product.get()).orElseThrow(
                () -> new EcommAPIException(HttpStatus.NOT_FOUND,
                        "Product does not exist in the cart")
        ));

        cart.get().setQuantity(cartDto.getQuantity());
        cart.get().setCreatedDate(new Date());
        Cart updatedCart = cartRepository.save(cart.get()) ;
        CartDto updatedCartDto = new CartDto(updatedCart.getId(),
                updatedCart.getProduct().getId(), updatedCart.getQuantity()) ;

        return updatedCartDto ;
    }

    @Override
    public void deleteCartItem(Long id) {
      if( !cartRepository.existsById(id)){
          throw new ResourceNotFoundException("Cart Item", "Cart Id", id) ;
      }
      cartRepository.deleteById(id);
    }


}
