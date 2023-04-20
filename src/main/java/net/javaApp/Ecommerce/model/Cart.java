package net.javaApp.Ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne( targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Integer quantity ;

    private Date createdDate ;

    public Cart(User user, Product product, Integer quantity, Date createdDate ) {
        this.user = user;
        this.product = product ;
        this.quantity = quantity ;
        this.createdDate = createdDate ;
    }
}
