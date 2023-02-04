package net.javaApp.Ecommerce.service.impl;

import net.javaApp.Ecommerce.exception.EcommAPIException;
import net.javaApp.Ecommerce.model.Role;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.LoginDto;
import net.javaApp.Ecommerce.payload.RegisterDto;
import net.javaApp.Ecommerce.repository.RoleRepository;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.security.JwtTokenProvider;
import net.javaApp.Ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RoleRepository roleRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private JwtTokenProvider jwtTokenProvider ;

    @Override
    public String login(LoginDto loginDto) {
       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        )) ;

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication) ;
        return token;
    }

    @Override
    public String registerBuyer(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
           throw new EcommAPIException(HttpStatus.BAD_REQUEST, "Username already exists!") ;
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "User for this email is already registered!") ;
        }

        User user = new User() ;
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setUsername(registerDto.getUsername());

        Set<Role> roles = new HashSet<>() ;
        Role userRole = roleRepository.findByName("ROLE_BUYER").get() ;
        roles.add(userRole) ;
        user.setRoles(roles);

        userRepository.save(user) ;
        return "User registered successfully";
    }

    @Override
    public String registerSeller(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "Username already exists!") ;
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "User for this email is already registered!") ;
        }

        User user = new User() ;
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setUsername(registerDto.getUsername());

        Set<Role> roles = new HashSet<>() ;
        Role userRole = roleRepository.findByName("ROLE_SELLER").get() ;
        roles.add(userRole) ;
        user.setRoles(roles);

        userRepository.save(user) ;
        return "User registered successfully";
    }
}