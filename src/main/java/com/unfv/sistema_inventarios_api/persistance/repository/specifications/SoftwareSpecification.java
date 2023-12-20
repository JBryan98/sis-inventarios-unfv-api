package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Software;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class SoftwareSpecification implements Specification<Software> {
    private String referencia;
    private String subcategoria;

    @Override
    public Predicate toPredicate(Root<Software> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Software, Subcategoria> softwareSubcategoriaJoin = root.join("subcategoria");

        if(StringUtils.hasText(referencia)){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("nombre"), "%" + referencia +  "%"),
                    criteriaBuilder.like(softwareSubcategoriaJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if (StringUtils.hasText(subcategoria)){
            Predicate subcategoriaLikePredicate = criteriaBuilder.like(softwareSubcategoriaJoin.get("nombre"), "%" + subcategoria + "%");
            predicates.add(subcategoriaLikePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
