package net.javaApp.Ecommerce.repository;

import net.javaApp.Ecommerce.model.User;
import org.apache.catalina.startup.Bootstrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByEmail(String email) ;

    Optional<User>findByUsername(String username) ;

    Optional<User>findByUsernameOrEmail( String username, String email) ;

    Boolean existsByUsername(String username) ;

    Boolean existsByEmail(String email) ;
}
