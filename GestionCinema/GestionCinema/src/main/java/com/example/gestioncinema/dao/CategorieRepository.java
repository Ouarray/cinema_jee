package com.example.gestioncinema.dao;

import com.example.gestioncinema.Entity.Categorie;
import com.example.gestioncinema.Entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// toute les methodes que j'ai heriter via le JPARepository accesible via une API REST
@RepositoryRestResource
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

}
