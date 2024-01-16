package com.example.gestioncinema.dao;

import com.example.gestioncinema.Entity.Cinema;
import com.example.gestioncinema.Entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface VilleRepository extends JpaRepository<Ville,Long> {
    
}
