package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDto {
    @NotNull
    String usernameOrEmail;

    @NotNull
    String password ;
}
