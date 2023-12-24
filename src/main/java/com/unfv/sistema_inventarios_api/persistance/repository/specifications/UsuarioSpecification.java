package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Rol;
import com.unfv.sistema_inventarios_api.persistance.entity.Usuario;
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
public class UsuarioSpecification implements Specification<Usuario> {
    private String referencia;
    private String rol;
    @Override
    public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Usuario, Rol> usuarioRolJoin = root.join("roles");
        if(StringUtils.hasText(referencia)){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("nombres"), "%" + referencia + "%"),
                    criteriaBuilder.like(root.get("apellidos"), "%" + referencia + "%"),
                    criteriaBuilder.like(root.get("email"), "%" + referencia + "%"),
                    criteriaBuilder.like(root.get("dni"), "%" + referencia + "%"),
                    criteriaBuilder.like(usuarioRolJoin.get("nombre"), "%" + referencia + "%")
            ));
        }

        if(StringUtils.hasText(rol)){
            Predicate rolLikePredicate = criteriaBuilder.like(usuarioRolJoin.get("nombre"), "%" + rol + "%");
            predicates.add(rolLikePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
