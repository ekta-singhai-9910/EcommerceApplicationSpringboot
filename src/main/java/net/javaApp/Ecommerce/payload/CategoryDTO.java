package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {

    @NotNull
    String category ;
}
