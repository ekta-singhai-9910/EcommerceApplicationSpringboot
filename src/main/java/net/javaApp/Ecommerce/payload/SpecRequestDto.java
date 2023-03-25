package net.javaApp.Ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecRequestDto {
    private List<SearchRequestDto> searchRequestDto ;
    private GlobalOperator globalOperator ;
    public enum GlobalOperator{
        AND, OR ;
    }
}
