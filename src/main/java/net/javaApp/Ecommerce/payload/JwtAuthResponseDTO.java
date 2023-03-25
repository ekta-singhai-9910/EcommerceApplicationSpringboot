package net.javaApp.Ecommerce.payload;

import lombok.*;
import net.javaApp.Ecommerce.model.RefreshToken;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDTO {
    private String accessToken ;
    private String tokenType = "Bearer" ;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;

    public JwtAuthResponseDTO(String accessToken, String refreshToken, String username, String email, Long id){
       this.accessToken = accessToken ;
       this.refreshToken = refreshToken ;
       this.username = username ;
       this.email = email ;
       this.id = id ;
    }

}
