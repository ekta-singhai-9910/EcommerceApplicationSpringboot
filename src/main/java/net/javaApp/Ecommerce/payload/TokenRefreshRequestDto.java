package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequestDto {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
