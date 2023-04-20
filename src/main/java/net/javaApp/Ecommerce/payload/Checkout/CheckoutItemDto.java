package net.javaApp.Ecommerce.payload.Checkout;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    private int  quantity;
    private long productId;
}
