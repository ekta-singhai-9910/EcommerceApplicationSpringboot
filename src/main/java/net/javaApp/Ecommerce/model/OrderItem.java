package net.javaApp.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private Date createdDate;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int price ;

    private int quantity ;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order ;
}
