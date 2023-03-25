package net.javaApp.Ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EcommAPIException extends RuntimeException{

    private HttpStatus status ;
    private String message ;

    public EcommAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message ;
    }

    public EcommAPIException(String message, HttpStatus status, String message1) {
        super(message) ;
        this.status = status;
        this.message = message1 ;
    }


    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
