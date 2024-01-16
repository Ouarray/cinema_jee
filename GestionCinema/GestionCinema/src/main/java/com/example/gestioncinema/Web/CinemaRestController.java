package com.example.gestioncinema.Web;

import com.example.gestioncinema.Entity.Film;
import com.example.gestioncinema.Entity.Ticket;
import com.example.gestioncinema.dao.FilmRepository;
import com.example.gestioncinema.dao.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//pour consulter les images
//API Rest
//Spring Data Rest
//quand il y a une association b derectionnel et serialiser en format JSON en tombe a une boucle infinit
@RestController
@CrossOrigin("*")//tout les domaine peuvent interarager avec la partit backend
public class CinemaRestController {
       /*@Autowired
       private FilmRepository filmRepository;
       @GetMapping("/listfilms")
       public List<Film> films(){
           return filmRepository.findAll();
       }*/
       @Autowired
       private FilmRepository filmRepository;
       @Autowired
       private TicketRepository ticketRepository;
       @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)//return des donnees de format image
       public byte[] image(@PathVariable(name = "id") Long id) throws Exception{
          Film f=filmRepository.findById(id).get();
          String  photoName= f.getPhoto();
          //le dossier de utilisateur
          File file=new File(System.getProperty("user.home")+"/Cinema/images/"+photoName+".jpg");
          Path path = Paths.get(file.toURI());
          return Files.readAllBytes(path);
       }

       @PostMapping("/payerTickets")
       @Transactional
       public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
           List<Ticket> ticketLists =new ArrayList<>();
           ticketForm.getTickets().forEach(idTicket->{
               Ticket ticket=ticketRepository.findById(idTicket).get();
               ticket.setNomClient(ticketForm.getNomClient());
               ticket.setReserve(true);
               ticket.setCodePayement(ticketForm.getCodePayment());
               ticketRepository.save(ticket);
               ticketLists.add(ticket);
           });
           //liste des tickets qui son vendus
           return ticketLists;
       }

}
@Data
class TicketForm{
    private String nomClient;
    private int codePayment;
    private List<Long> tickets = new ArrayList<>();


}