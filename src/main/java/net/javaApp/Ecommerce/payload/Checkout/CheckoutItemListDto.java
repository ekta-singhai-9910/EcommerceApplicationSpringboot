package net.javaApp.Ecommerce.payload.Checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemListDto {
    List<CheckoutItemDto> checkoutItemDtoList ;
}
