package net.javaApp.Ecommerce.payload;

import lombok.Data;

@Data
public class JwtAuthResponseDTO {
    private String accessToken ;
    private String tokenType = "Bearer" ;
}
