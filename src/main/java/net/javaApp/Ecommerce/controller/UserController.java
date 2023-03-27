package net.javaApp.Ecommerce.controller;

import net.javaApp.Ecommerce.payload.CreateRoleDto;
import net.javaApp.Ecommerce.payload.CreateRoleResponseDto;
import net.javaApp.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody CreateRoleDto createRoleDto){
        CreateRoleResponseDto responseDto = userService.createRole(createRoleDto) ;
        return new ResponseEntity(responseDto, HttpStatus.CREATED) ;
    }


    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(){
        return null ;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> placeOrder(){
       return null ;
    }
}
