package net.javaApp.Ecommerce.repository;


import net.javaApp.Ecommerce.model.Cart;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<List<Cart>> findAllByUserOrderByCreatedDateDesc(User user) ;

    @Transactional
    List<Cart> deleteByUser(User user) ;

    Optional<Cart> findByUserAndProduct(User user, Product product) ;


}
