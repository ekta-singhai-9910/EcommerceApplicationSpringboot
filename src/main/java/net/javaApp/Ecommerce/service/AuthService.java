package net.javaApp.Ecommerce.service;

import net.javaApp.Ecommerce.payload.LoginDto;
import net.javaApp.Ecommerce.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto) ;

    String register(RegisterDto registerDto) ;
}
