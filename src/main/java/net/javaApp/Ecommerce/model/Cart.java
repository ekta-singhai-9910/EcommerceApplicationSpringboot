package net.javaApp.Ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems ;



}
