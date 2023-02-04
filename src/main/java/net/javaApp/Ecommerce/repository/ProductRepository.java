package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
   List<Product> findAll() ;
   List<Product> findByCategoryId(long Id) ;
}
