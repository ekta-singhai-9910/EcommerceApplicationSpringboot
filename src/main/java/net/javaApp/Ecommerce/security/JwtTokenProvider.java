package net.javaApp.Ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.javaApp.Ecommerce.exception.EcommAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date() ;
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate) ;

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .compact() ;

        return token ;

    }

    private  Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecretKey)
        ) ;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody() ;

        String username = claims.getSubject();
        return username ;
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token) ;

            return true ;
        } catch (MalformedJwtException ex){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token") ;
        } catch (ExpiredJwtException ex){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token") ;
        } catch (UnsupportedJwtException ex){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token") ;
        } catch (IllegalArgumentException ex){
            throw new EcommAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty") ;
        }
    }
}
