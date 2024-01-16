package com.example.gestioncinema;

import com.example.gestioncinema.Entity.Film;
import com.example.gestioncinema.Entity.Salle;
import com.example.gestioncinema.Entity.Ticket;
import com.example.gestioncinema.Service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class GestionCinemaApplication implements CommandLineRunner {

    @Autowired
    private ICinemaInitService cinemaInitService;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
    public static void main(String[] args) {
        SpringApplication.run(GestionCinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
             repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
             cinemaInitService.initVilles();
             cinemaInitService.initCinemas();
             cinemaInitService.initSalles();
             cinemaInitService.initPlaces();
             cinemaInitService.initSeances();
             cinemaInitService.initCategories();
             cinemaInitService.initFilms();
             cinemaInitService.initProjections();
             cinemaInitService.initTickets();
    }
}
