package com.example.gestioncinema.Entity;

import com.example.gestioncinema.Entity.Place;
import com.example.gestioncinema.Entity.Projection;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomClient;
    private double prix;
    //il accepte les valeur null
    @Column(unique = false,nullable = true)
    private Integer codePayement;
    // int: la valeur par default c'est 0
    // integer: la valeur par default c'est null
    private boolean reserve;
    @ManyToOne
    private Place place;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Projection projection;
}
