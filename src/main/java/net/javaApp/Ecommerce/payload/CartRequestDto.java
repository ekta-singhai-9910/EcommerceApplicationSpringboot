package net.javaApp.Ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {
    String productName ;
    Long productId ;
}
