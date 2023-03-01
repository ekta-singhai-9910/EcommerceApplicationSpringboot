package net.javaApp.Ecommerce.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.javaApp.Ecommerce.exception.TokenRefreshException;
import net.javaApp.Ecommerce.model.RefreshToken;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.*;
import net.javaApp.Ecommerce.repository.UserRepository;
import net.javaApp.Ecommerce.security.JwtTokenProvider;
import net.javaApp.Ecommerce.security.RefreshTokenService;
import net.javaApp.Ecommerce.security.UserDetailsImpl;
import net.javaApp.Ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService ;

    @Autowired
    private JwtTokenProvider jwtTokenProvider ;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto) ;
        JwtAuthResponseDTO responseDTO = new JwtAuthResponseDTO() ;
        responseDTO.setAccessToken(token);
        Optional<User> user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()) ;
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.get().getId()) ;
      responseDTO.setRefreshToken(refreshToken.getToken());
        responseDTO.setUsername(user.get().getUsername());
        responseDTO.setEmail(user.get().getEmail());
        responseDTO.setId(user.get().getId());
       return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }

    @PostMapping(value = {"/register/buyer", "/signup/buyer"})
    public ResponseEntity<?> registerBuyer(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.registerBuyer(registerDto), HttpStatus.OK) ;
    }

    @PostMapping(value = {"/register/seller", "/signup/seller"})
    public ResponseEntity<?> registerSeller(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.registerSeller(registerDto), HttpStatus.OK) ;
    }


    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request){
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user-> {
                    String token = jwtTokenProvider.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenResponseDto(token, requestRefreshToken));
                }).orElseThrow(()->new TokenRefreshException(requestRefreshToken,
                        "Refresh token not found in the dataabse")) ;
    }


}
