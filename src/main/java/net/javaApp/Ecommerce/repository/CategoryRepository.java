package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.Category;
import net.javaApp.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
