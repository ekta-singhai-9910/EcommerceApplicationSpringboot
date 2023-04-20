package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
