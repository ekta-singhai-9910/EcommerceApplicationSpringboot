package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;


public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserIdOrderByCreatedDateDesc(Long userId) ;
}
