package net.javaApp.Ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    private int price ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller ;


    private Long quantity ;

    @OneToMany(mappedBy = "product")
    private Set<CartItem>cartItems ;

}
