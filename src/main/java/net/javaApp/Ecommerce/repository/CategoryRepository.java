package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findById(Long id) ;
  Category findByName(String name) ;
}
