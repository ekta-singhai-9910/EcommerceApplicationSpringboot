package net.javaApp.Ecommerce.repository;

import jakarta.transaction.Transactional;
import net.javaApp.Ecommerce.model.RefreshToken;
import net.javaApp.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser_id(Long userId) ;

    @Modifying
    int deleteByUser(User user);

    @Transactional
    int deleteById(Long id) ;
    @Modifying
    @Transactional
    @Query("Delete From RefreshToken where User=:user")
    int deleteByUserObj( User user) ;

}
