package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
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
public class SubcategoriaSpecification implements Specification<Subcategoria>{
    private String referencia;
    private String nombre;
    private String categoria;

    @Override
    public Predicate toPredicate(Root<Subcategoria> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Subcategoria, Categoria> subcategoriaCategoriaJoin = root.join("categoria");

        if(StringUtils.hasText(referencia)){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("nombre"), "%" + referencia + "%"),
                    criteriaBuilder.like(subcategoriaCategoriaJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if(StringUtils.hasText(nombre)){
            Predicate nombreLikePredicate = criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%");
            predicates.add(nombreLikePredicate);
        }
        if(StringUtils.hasText(categoria)){
            Predicate categoriaLikePredicate = criteriaBuilder.like(subcategoriaCategoriaJoin.get("nombre"), "%" + categoria + "%");
            predicates.add(categoriaLikePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
