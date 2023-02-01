package net.javaApp.Ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Date timeStamp ;
    private String details ;
    private String message ;
}
