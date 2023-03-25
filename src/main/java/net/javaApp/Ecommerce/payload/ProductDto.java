package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.javaApp.Ecommerce.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long productId ;
    @NotEmpty
    private String name ;
    @NotNull
    private int price ;
    @NotEmpty
    private String usernameOrEmail ;
    @NotNull
    private String category;
    @NotNull
    private Long quantity;
}
