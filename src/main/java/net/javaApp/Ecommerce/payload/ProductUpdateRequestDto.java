package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDto {
   @NotNull
   private Long id ;
   private String name = null;
   private int price = -1;
   private String category = null;
   private Long quantity = -1L ;

}
