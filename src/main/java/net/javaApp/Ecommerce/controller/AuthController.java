package net.javaApp.Ecommerce.controller;

import net.javaApp.Ecommerce.payload.LoginDto;
import net.javaApp.Ecommerce.payload.RegisterDto;
import net.javaApp.Ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService ;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
       return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK) ;
    }

    @PostMapping(value = {"/register/buyer", "/signup/buyer"})
    public ResponseEntity<?> registerBuyer(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.registerBuyer(registerDto), HttpStatus.OK) ;
    }

    @PostMapping(value = {"/register/seller", "/signup/seller"})
    public ResponseEntity<?> registerSeller(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.registerSeller(registerDto), HttpStatus.OK) ;
    }
}
