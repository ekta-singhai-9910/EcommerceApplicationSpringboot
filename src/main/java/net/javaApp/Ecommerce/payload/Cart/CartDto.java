package net.javaApp.Ecommerce.payload.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id ;
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity ;
}
