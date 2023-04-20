package net.javaApp.Ecommerce.utils;

import lombok.experimental.UtilityClass;
import net.javaApp.Ecommerce.model.Product;
import net.javaApp.Ecommerce.model.User;
import net.javaApp.Ecommerce.payload.ProductDto;
import net.javaApp.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@UtilityClass
public class CommonUtil {

    @Autowired
    private UserDetailsService userDetailsService ;

    @Autowired
    private UserRepository userRepository ;

    public long getUserIdFromToken(){
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName()) ;
        Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername()) ;
        long userId = user.get().getId();
        return userId ;
    }

    public ProductDto getProductDtoFromProduct(Product product){
        ProductDto productDto = new ProductDto() ;
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setUsernameOrEmail(product.getSeller().getUsername());
        productDto.setPrice(product.getPrice());
        return productDto ;
    }

}
