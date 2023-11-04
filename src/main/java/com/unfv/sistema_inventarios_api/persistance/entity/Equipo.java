package com.unfv.sistema_inventarios_api.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
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

    @Column(unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @ManyToMany
    @JoinTable(
            name = "equipo_hardware",
            joinColumns = {
                    @JoinColumn(name = "id_equipo", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_hardware", referencedColumnName = "id")
            }
    )
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
