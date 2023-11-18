package com.unfv.sistema_inventarios_api.persistance.repository.specifications;

import com.unfv.sistema_inventarios_api.persistance.entity.Categoria;
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
public class ModeloSpecification implements Specification<Modelo> {
    private List<String> subcategorias;
    private String categoria;
    private String nombre;
    private String marca;

    @Override
    public Predicate toPredicate(Root<Modelo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Modelo, Subcategoria> modeloSubcategoriaJoin = root.join("subcategoria");
        Join<Modelo, Marca> modeloMarcaJoin = root.join("marca");
        Join<Subcategoria, Categoria> subcategoriaCategoriaJoin = modeloSubcategoriaJoin.join("categoria");

        if(StringUtils.hasText(nombre)){
            Predicate nombreLikePredicate = criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%");
            predicates.add(nombreLikePredicate);
        }

        if(subcategorias != null && !subcategorias.isEmpty()){
            List<Predicate> subcategoriaPredicates = new ArrayList<>();
            for(String subcategoria : subcategorias){
                Predicate componenteLikeCategoria = criteriaBuilder.like(modeloSubcategoriaJoin.get("nombre"), "%" + subcategoria + "%");
                subcategoriaPredicates.add(componenteLikeCategoria);
            }
            Predicate categoriaOrPredicate = criteriaBuilder.or(subcategoriaPredicates.toArray(new Predicate[0]));
            predicates.add(categoriaOrPredicate);
        }

        if(StringUtils.hasText(categoria)){
            Predicate categoriaLikePredicate = criteriaBuilder.like(subcategoriaCategoriaJoin.get("nombre"), "%" + categoria + "%");
            predicates.add(categoriaLikePredicate);
        }

        if(StringUtils.hasText(marca)){
            Predicate componenteLikeMarca = criteriaBuilder.like(modeloMarcaJoin.get("nombre"), "%" + marca + "%");
            predicates.add(componenteLikeMarca);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
