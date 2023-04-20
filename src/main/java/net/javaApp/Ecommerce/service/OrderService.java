package net.javaApp.Ecommerce.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import net.javaApp.Ecommerce.model.Order;
import net.javaApp.Ecommerce.payload.Checkout.CheckoutItemDto;
import net.javaApp.Ecommerce.payload.Checkout.CheckoutItemListDto;
import net.javaApp.Ecommerce.payload.Checkout.OrderPlacedDto;

import java.util.List;

public interface OrderService {

     public void placeOrder() ;

     public List<Order> getAllOrderByUser() ;

     public Order getOrderById(Long id) ;
}
