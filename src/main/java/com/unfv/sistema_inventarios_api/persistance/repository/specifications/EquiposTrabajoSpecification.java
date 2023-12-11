package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Slf4j
public class EquiposTrabajoSpecification implements Specification<EquiposTrabajo> {
    private String estado;

    @Override
    public Predicate toPredicate(Root<EquiposTrabajo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.hasText(estado)){
            Predicate estadoLikePredicate = criteriaBuilder.like(root.get("estado"), "%" + estado + "%");
            predicates.add(estadoLikePredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
