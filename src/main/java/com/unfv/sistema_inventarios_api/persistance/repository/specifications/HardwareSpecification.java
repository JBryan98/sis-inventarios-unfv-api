package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
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
public class HardwareSpecification implements Specification<Hardware> {
    private String estado;
    private String serie;
    private List<String> categorias;
    private String modelo;
    private String marca;

    @Override
    public Predicate toPredicate(Root<Hardware> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Hardware, Modelo> componenteModeloJoin = root.join("modelo");
        Join<Modelo, Categoria> modeloCategoriaJoin = componenteModeloJoin.join("categoria");
        Join<Modelo, Marca> modeloMarcaJoin = componenteModeloJoin.join("marca");

        if (StringUtils.hasText(estado)) {
            Predicate estadoLikePredicate = criteriaBuilder.like(root.get("estado"), "%" + estado + "%");
            predicates.add(estadoLikePredicate);
        }

        if (StringUtils.hasText(modelo)) {
            Predicate componenteLikePredicate = criteriaBuilder.like(componenteModeloJoin.get("nombre"), "%" + modelo + "%");
            predicates.add(componenteLikePredicate);
        }

        if(categorias != null && !categorias.isEmpty()){
            List<Predicate> categoriaPredicates = new ArrayList<>();
            for(String categoria : categorias){
                Predicate componenteLikeCategoria = criteriaBuilder.like(modeloCategoriaJoin.get("nombre"), "%" + categoria + "%");
                categoriaPredicates.add(componenteLikeCategoria);
            }
            Predicate categoriaOrPredicate = criteriaBuilder.or(categoriaPredicates.toArray(new Predicate[0]));
            predicates.add(categoriaOrPredicate);
        }

        if(StringUtils.hasText(marca)){
            Predicate componenteLikeMarca = criteriaBuilder.like(modeloMarcaJoin.get("nombre"), "%" + marca + "%");
            predicates.add(componenteLikeMarca);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));    }
}
