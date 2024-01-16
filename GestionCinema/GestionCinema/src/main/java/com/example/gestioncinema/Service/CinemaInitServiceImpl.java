package com.example.gestioncinema.Service;

import com.example.gestioncinema.Entity.*;
import com.example.gestioncinema.dao.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Override
    public void initVilles() {
        Stream.of("Casablanca","Marrakech","Rabat","Tanger","Agadir","Fes","Esaouira").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
            //villeRepository.save(new Ville(null,v));
        });
    }

    @Override
    public void initCinemas() {
       villeRepository.findAll().forEach(v->{
           Stream.of("MegaRama","IMAX","FOUNOUN","CHAHRAZAD","DAOULIZ")
                   .forEach(nameCinema->{
                       Cinema cinema=new Cinema();
                       cinema.setName(nameCinema);
                       cinema.setNombreSalles(10+(int)Math.random()*7);
                       cinema.setVille(v);
                       cinemaRepository.save(cinema);
                   });
       });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema->{
            for (int i=0;i<cinema.getNombreSalles();i++){
                Salle salle = new Salle();
                salle.setName("Salle"+(i+1));
                salle.setCinema(cinema);
                //min 15 et max 20
                salle.setNombrePlace(15+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }


    @Override
    public void initPlaces() {
            salleRepository.findAll().forEach(salle->{
                for (int i=0;i<salle.getNombrePlace();i++) {
                    Place place=new Place();
                    place.setNumero(i+1);
                    place.setSalle(salle);
                    placeRepository.save(place);
                }
            });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat= new SimpleDateFormat("HH:mm");
         Stream.of("12:00","15:00","17:00","19:00","21:00")
                 .forEach(s->{
                     Seance seance=new Seance();
                     try {
                         seance.setHeureDebut(dateFormat.parse(s));
                         seanceRepository.save(seance);
                     } catch (ParseException e) {
                         throw new RuntimeException(e);
                     }
                 });
    }

    @Override
    public void initCategories() {
           Stream.of("Histoire","Action","Fiction","Drama")
                   .forEach(cat->{
               Categorie categorie=new Categorie();
               categorie.setName(cat);
               categorieRepository.save(categorie);

           });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[] {1,1.5,2,2.5,3};
        List<Categorie> categories = categorieRepository.findAll();
          Stream.of("Acide","Barbaque","Smile","Tempete")
                  .forEach(f->{
                      Film film=new Film();
                      film.setTitre(f);
                      film.setDuree(durees[new Random().nextInt(durees.length)]);
                      //supprimer les espaces.
                      film.setPhoto(f.replaceAll(" ",""));
                      film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                      filmRepository.save(film);
                  });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[]{30,40,50,70,80,90,100};
        List<Film> films=filmRepository.findAll();
     //chaque ville il y a des cinema et pour chaque cinema il y a des salle.
        villeRepository.findAll().forEach(ville->{
            ville.getCinemas().forEach(cinema-> {
                cinema.getSalles().forEach(salle-> {
                    //pour chaque salle je prend aliatoirement un film
                    int index = new Random().nextInt(films.size());
                    Film film= films.get(index);
                    //pour chaque salle en prend tout les films
                    //filmRepository.findAll().forEach(film-> {
                        //pour chaque seance je vait deffinire une projection.
                        seanceRepository.findAll().forEach(seance-> {
                            Projection projection= new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });
                });
            });
        });

    }

    @Override
    public void initTickets() {
            projectionRepository.findAll().forEach(p -> {
                p.getSalle().getPlaces().forEach(place -> {
                    Ticket ticket=new Ticket();

                    ticket.setPlace(place);
                    ticket.setPrix(p.getPrix());
                    ticket.setProjection(p);
                    ticket.setReserve(false);
                    ticketRepository.save(ticket);
                });
            });
    }
}
