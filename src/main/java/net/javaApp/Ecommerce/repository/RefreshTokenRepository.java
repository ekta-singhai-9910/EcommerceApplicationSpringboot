package net.javaApp.Ecommerce.repository;

import jakarta.transaction.Transactional;
import net.javaApp.Ecommerce.model.RefreshToken;
import net.javaApp.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);



    @Transactional
    @Modifying
    int deleteByUser(User user);


}
