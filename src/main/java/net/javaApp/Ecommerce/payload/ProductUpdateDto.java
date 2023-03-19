package net.javaApp.Ecommerce.payload;

import lombok.Data;

@Data
public class ProductUpdateDto {

    private Long quantity = null;
    private int price = -1;
    private String category = null ;
    private String name = null ;
}
