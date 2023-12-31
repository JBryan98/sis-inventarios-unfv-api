package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Equipo;
import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class EquipoSpecification implements Specification<Equipo> {
    private String referencia;
    private String estado;
    private String ubicacion;

    @Override
    public Predicate toPredicate(Root<Equipo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<Equipo, Ubicacion> equipoUbicacionJoin = root.join("ubicacion", JoinType.LEFT);

        if(StringUtils.hasText(referencia)){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("estado"), "%" + referencia + "%"),
                    criteriaBuilder.like(root.get("nombre"), "%" + referencia + "%"),
                    criteriaBuilder.like(equipoUbicacionJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if(StringUtils.hasText(ubicacion)){
            Predicate ubicacionPredicate = criteriaBuilder.like(equipoUbicacionJoin.get("nombre"), "%" + ubicacion + "%");
            predicates.add(ubicacionPredicate);
        }

        if(StringUtils.hasText(estado)){
            Predicate estadoLikePredicate = criteriaBuilder.like(root.get("estado"), "%" + estado + "%");
            predicates.add(estadoLikePredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
