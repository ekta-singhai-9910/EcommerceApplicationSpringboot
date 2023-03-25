package net.javaApp.Ecommerce.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.javaApp.Ecommerce.payload.SearchRequestDto;
import net.javaApp.Ecommerce.payload.SpecRequestDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FilterSpecification<T> {

    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue()) ;
            }
        } ;
   }



    public Specification<T>getSearchSpecification(List<SearchRequestDto> searchRequestDtos, SpecRequestDto.GlobalOperator globalOperator){
       return (root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>() ;
          for( SearchRequestDto searchRequestDto : searchRequestDtos){


              switch(searchRequestDto.getOperation()){
                  case EQUAL :
                      Predicate equal = criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue()) ;
                      predicates.add(equal) ;
                      break ;

                  case LIKE :
                      Predicate like = criteriaBuilder.like(root.get(searchRequestDto.getColumn()),  "%" + searchRequestDto.getValue() + "%") ;
                      predicates.add(like) ;
                      break ;

                  case IN:
                      String[] split = searchRequestDto.getValue().split(",") ;
                      Predicate in = root.get(searchRequestDto.getColumn()).in(Arrays.asList(split)) ;
                      predicates.add(in) ;
                      break ;

                  case GREATER_THAN:
                      Predicate greaterThan = criteriaBuilder.greaterThan(root.get(searchRequestDto.getColumn()),  searchRequestDto.getValue()) ;
                      predicates.add(greaterThan) ;
                      break ;

                  case LESS_THAN:
                      Predicate lessThan = criteriaBuilder.lessThan(root.get(searchRequestDto.getColumn()),  searchRequestDto.getValue()) ;
                      predicates.add(lessThan) ;
                      break ;

                  case BETWEEN:
                      String[] split1 = searchRequestDto.getValue().split(",") ;
                      Predicate between = criteriaBuilder.between(root.get(searchRequestDto.getColumn()), split1[0], split1[1]) ;
                      predicates.add(between) ;
                      break ;

                  case JOIN:
                      Predicate join = criteriaBuilder.equal(root.join(searchRequestDto.getJoinTable()).get(searchRequestDto.getColumn()), searchRequestDto.getValue()) ;
                      predicates.add(join) ;
                      break ;

                  default:
                      throw new IllegalStateException("Unexpected Value" + "") ;
              }
          }
          if(globalOperator.equals(SpecRequestDto.GlobalOperator.AND)){
              return criteriaBuilder.and(predicates.toArray(new Predicate[0])) ;
          }else {
              return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
          }
       } ;
    }
}
