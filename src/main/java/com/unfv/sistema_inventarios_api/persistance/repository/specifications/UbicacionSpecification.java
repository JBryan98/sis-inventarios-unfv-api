package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Facultad;
import com.unfv.sistema_inventarios_api.persistance.entity.Ubicacion;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
public class UbicacionSpecification implements Specification<Ubicacion> {
    private String referencia;
    private String facultad;

    @Override
    public Predicate toPredicate(Root<Ubicacion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Ubicacion, Facultad> ubicacionFacultadJoin = root.join("facultad");

        if(StringUtils.hasText(referencia)){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("nombre"), "%" + referencia + "%"),
                    criteriaBuilder.like(ubicacionFacultadJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if(StringUtils.hasText(facultad)){
            Predicate ubicacionLikePredicate = criteriaBuilder.like(ubicacionFacultadJoin.get("nombre"), "%" + facultad +"%");
            predicates.add(ubicacionLikePredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
