package net.javaApp.Ecommerce.controller;


import net.javaApp.Ecommerce.model.Order;
import net.javaApp.Ecommerce.payload.GenericResponseDto;
import net.javaApp.Ecommerce.service.AuthService;
import net.javaApp.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private AuthService authService ;

    @Autowired
    private OrderService orderService ;

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @PostMapping
    public ResponseEntity<?> placeOrder(){
        orderService.placeOrder();
        return new ResponseEntity<>(new GenericResponseDto(true, "Your" +
                " order has been placed successfully"), HttpStatus.CREATED) ;
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(){
       List<Order> orderList = orderService.getAllOrderByUser() ;
       return new ResponseEntity<>(orderList, HttpStatus.OK) ;
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable(name = "orderId") Long orderId){
       Order order = orderService.getOrderById(orderId) ;
       return new ResponseEntity<>(order, HttpStatus.OK) ;
    }
}
