package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.ResourceNotFoundException;
import net.javaApp.Ecommerce.model.Order;
import net.javaApp.Ecommerce.model.OrderItem;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.Cart.CartDto;
import net.javaApp.Ecommerce.payload.Checkout.OrderPlacedDto;
import net.javaApp.Ecommerce.repository.*;
import net.javaApp.Ecommerce.service.AuthService;
import net.javaApp.Ecommerce.service.CartService;
import net.javaApp.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService ;

    @Autowired
    private AuthService authService ;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ProductRepository productRepository ;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public void placeOrder() {
        long userId = authService.getUserIdFromToken();
        Optional<User> user = userRepository.findById(userId) ;
        List<CartDto> cartDtoList = cartService.getAllCartItems(userId) ;
        Order order = new Order() ;
        order.setCreatedDate(new Date());
        order.setUser(user.get());
        Integer price = 0 ;

        OrderPlacedDto orderPlacedDto = new OrderPlacedDto();
        for( CartDto cartDto: cartDtoList){
            Optional<Product> product = productRepository.findById(cartDto.getProductId()) ;
            price += cartDto.getQuantity() + product.get().getPrice() ;
            OrderItem orderItem = new OrderItem() ;
            orderItem.setPrice(product.get().getPrice());
            orderItem.setQuantity(cartDto.getQuantity());
            orderItem.setCreatedDate(new Date());
            orderItem.setProduct(product.get());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem) ;
        }

        orderRepository.save(order) ;
        order.setTotalPrice(price);
        cartRepository.deleteByUser(user.get()) ;

    }

    @Override
    public List<Order> getAllOrderByUser() {
        long userId = authService.getUserIdFromToken() ;
        Optional<User> user = userRepository.findById(userId) ;
        List<Order> orders = orderRepository.findAllByUserOrderByCreatedDateDesc(user.get()) ;
        return orders ;
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id) ;
        if(order.isPresent()){
            return order.get() ;
        }
        else throw new ResourceNotFoundException("Order", "Order Id", id) ;
    }
}
