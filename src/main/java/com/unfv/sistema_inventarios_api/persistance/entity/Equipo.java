package com.unfv.sistema_inventarios_api.persistance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "equipos")
public class Equipo extends Auditoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "equipo")
    private Set<Hardware> hardware = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "equipo_software",
            joinColumns = {
                    @JoinColumn(name = "id_equipo", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_software", referencedColumnName = "id")
            }
    )
    private Set<Software> software = new HashSet<>();
}
