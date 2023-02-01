package net.javaApp.Ecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull
    private String name ;

    @NotNull
    @Size(min = 4, message = "Username should contain at least 4 characters")
    private String username ;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 4, message = "Password should be at least 4 characters")
    private String password ;
}
