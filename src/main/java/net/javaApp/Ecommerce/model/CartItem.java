package net.javaApp.Ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class CartItem {
    @Id
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;


}
