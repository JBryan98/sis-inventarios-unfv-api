package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.EquiposTrabajo;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
import com.unfv.sistema_inventarios_api.persistance.entity.Subcategoria;
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
public class EquiposTrabajoSpecification implements Specification<EquiposTrabajo> {
    private String referencia;
    private String estado;
    private String subcategoria;
    private String modelo;
    private String marca;

    @Override
    public Predicate toPredicate(Root<EquiposTrabajo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<EquiposTrabajo, Modelo> equiposTrabajoModeloJoin = root.join("modelo");
        Join<Modelo, Subcategoria> modeloSubcategoriaJoin = equiposTrabajoModeloJoin.join("subcategoria");
        Join<Modelo, Marca> modeloMarcaJoin = equiposTrabajoModeloJoin.join("marca");
        Join<EquiposTrabajo, Ubicacion> equiposTrabajoUbicacionJoin = root.join("ubicacion", JoinType.LEFT);

        if (StringUtils.hasText(referencia)) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("estado"), "%" + referencia + "%"),
                    criteriaBuilder.like(root.get("serie"), "%" + referencia + "%"),
                    criteriaBuilder.like(modeloSubcategoriaJoin.get("nombre"), "%" + referencia + "%"),
                    criteriaBuilder.like(equiposTrabajoUbicacionJoin.get("nombre"), "%" + referencia + "%"),
                    criteriaBuilder.like(equiposTrabajoModeloJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if(StringUtils.hasText(estado)){
            Predicate estadoLikePredicate = criteriaBuilder.like(root.get("estado"), "%" + estado + "%");
            predicates.add(estadoLikePredicate);
        }

        if(StringUtils.hasText(subcategoria)){
            Predicate subcategoriaPredicate = criteriaBuilder.like(modeloSubcategoriaJoin.get("nombre"), "%" + subcategoria + "%");
            predicates.add(subcategoriaPredicate);
        }

        if(StringUtils.hasText(modelo)){
            Predicate modeloPredicate = criteriaBuilder.like(equiposTrabajoModeloJoin.get("nombre"), "%" + modelo + "%");
            predicates.add(modeloPredicate);
        }

        if(StringUtils.hasText(marca)){
            Predicate marcaPredicate = criteriaBuilder.like(modeloMarcaJoin.get("marca"), "%" + marca + "%");
            predicates.add(marcaPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
