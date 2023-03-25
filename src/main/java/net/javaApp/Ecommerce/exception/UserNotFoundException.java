package net.javaApp.Ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
   String usernameOrEmail ;
   String userType ;

   public UserNotFoundException(String userType, String usernameOrEmail){
       super(String.format("%s not found for the username : %s", userType, usernameOrEmail));
       this.usernameOrEmail = usernameOrEmail ;
       this.userType = userType ;

   }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
}
