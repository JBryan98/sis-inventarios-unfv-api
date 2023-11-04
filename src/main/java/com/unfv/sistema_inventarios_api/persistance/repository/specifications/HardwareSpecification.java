package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Hardware;
import com.unfv.sistema_inventarios_api.persistance.entity.Marca;
import com.unfv.sistema_inventarios_api.persistance.entity.Modelo;
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
public class HardwareSpecification implements Specification<Hardware> {
    private String estado;
    private String serie;
    private List<String> subcategorias;
    private String modelo;
    private String marca;

    @Override
    public Predicate toPredicate(Root<Hardware> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Hardware, Modelo> componenteModeloJoin = root.join("modelo");
        Join<Modelo, Subcategoria> modeloCategoriaJoin = componenteModeloJoin.join("subcategoria");
        Join<Modelo, Marca> modeloMarcaJoin = componenteModeloJoin.join("marca");

        if (StringUtils.hasText(estado)) {
            Predicate estadoLikePredicate = criteriaBuilder.like(root.get("estado"), "%" + estado + "%");
            predicates.add(estadoLikePredicate);
        }

        if (StringUtils.hasText(modelo)) {
            Predicate componenteLikePredicate = criteriaBuilder.like(componenteModeloJoin.get("nombre"), "%" + modelo + "%");
            predicates.add(componenteLikePredicate);
        }

        if(subcategorias != null && !subcategorias.isEmpty()){
            List<Predicate> subcategoriaPredicates = new ArrayList<>();
            for(String subcategoria : subcategorias){
                Predicate componenteLikeCategoria = criteriaBuilder.like(modeloCategoriaJoin.get("nombre"), "%" + subcategoria + "%");
                subcategoriaPredicates.add(componenteLikeCategoria);
            }
            Predicate categoriaOrPredicate = criteriaBuilder.or(subcategoriaPredicates.toArray(new Predicate[0]));
            predicates.add(categoriaOrPredicate);
        }

        if(StringUtils.hasText(marca)){
            Predicate componenteLikeMarca = criteriaBuilder.like(modeloMarcaJoin.get("nombre"), "%" + marca + "%");
            predicates.add(componenteLikeMarca);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));    }
}
