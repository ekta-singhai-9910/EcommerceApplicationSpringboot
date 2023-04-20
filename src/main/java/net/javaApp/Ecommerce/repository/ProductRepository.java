package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {


   Optional<Product> findById(long id) ;

   @Transactional
   @Modifying
   void deleteById(Long id) ;
}
