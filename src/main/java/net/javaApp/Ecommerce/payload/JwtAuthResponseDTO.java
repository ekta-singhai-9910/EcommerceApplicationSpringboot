package net.javaApp.Ecommerce.payload;

import lombok.Data;
import net.javaApp.Ecommerce.model.RefreshToken;

import java.util.List;

@Data
public class JwtAuthResponseDTO {
    private String accessToken ;
    private String tokenType = "Bearer" ;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;

}
