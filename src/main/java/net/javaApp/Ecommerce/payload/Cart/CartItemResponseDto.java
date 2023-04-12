package net.javaApp.Ecommerce.payload.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {

    private Long productId ;
    private Integer quantity ;
    private Long cartId ;

}
