package com.example.gestioncinema.Entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

import java.util.Date;
import java.util.List;
//interface qui utilise la notation projection
//en lui donne un nom et en lui dir que cet projection va s'appliquer la classe projection
@Projection(name = "p1", types = {com.example.gestioncinema.Entity.Projection.class})
public interface ProjectionProjection {
    public Long getId();
    public double getPrix();
    public Date getDateProjection();
    public Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public Collection<Ticket> getTickets();

}

