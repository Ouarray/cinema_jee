import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {CinemaService} from "../Services/cinema.service";

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})
export class CinemaComponent implements OnInit {

  public villes : any;
  public cinemas : any;
  public currentVille: any;
  public currentCinema: any;
  public salles: any;
  public currentProjection: any;
  public selectedTickets: any;
  constructor(public cinemaService:CinemaService) {}

  ngOnInit() {
    // Send an HTTP request to the backend to retrieve the list of cities
    this.cinemaService.getVilles()
      .subscribe(
        data => {
          this.villes=data;
          },
        error => {
          console.log(error);
        }
      );
  }

  onGetCinemas(v : any) {

    //j'appel les cinemas de cette ville
    this.currentVille=v;
    this.salles=undefined;
    this.cinemaService.getCinemas(v)
      .subscribe(data => {
          this.cinemas=data;
        },
        error => {
          console.log(error);
        }
      );
  }

  onGetSalles(c:any) {
      this.currentCinema=c;
      this.cinemaService.getSalles(c)
        .subscribe(data => {
          /*
          * j'ai recuperer la liste des salles et j'ai fait une boucle
          * pour chaque salle j'ai recuperer les projections de cette salle
          */
            this.salles=data;
            this.salles._embedded.salles.forEach((salle:any)=> {
              this.cinemaService.getProjection(salle)
                .subscribe(data => {
                    salle.projections=data;
                  },error => {
                    console.log(error);
                  })
            })
          }, error => {
            console.log(error);
          });
  }

  onGetTicketPlaces(p: any) {
    //je vait  deffinir les places d'une projection
    this.currentProjection=p;

    this.cinemaService.getTicketPlaces(p)
      .subscribe(data => {
      this.currentProjection.tickets=data;
      this.selectedTickets=[];
    },error => {
      console.log(error);
    });
  }

  onSelectTicket(t: any) {
    if (!t.selected){
      t.selected=true;
      this.selectedTickets.push(t);
    }else {
      t.selected=false;
      this.selectedTickets.splice(/*specifier position de l'element*/this.selectedTickets.indexOf(t),1);
    }
    //si je clique sur un ticket j'ajoute un ticket selected

       //ajouter a la liste

  }
  getTicketClass(t: any) {
    let str="btn ";
    if(t.reserve==true){
      str+="btn-danger";
    }else if (t.selected) {
      //si il est selected j'ai fait warning
      str+="btn-warning";
    }else {
      str+="btn-success";
    }
    return str;
  }

  onPayTicket(f:any) {
    let tickets : any ;
    tickets= [];
    this.selectedTickets.forEach((t:any)=>{
      tickets.push(t.id);
    });
    f.tickets=tickets;
    console.log(f);
    this.cinemaService.payerTickets(f)
      .subscribe(data => {
        alert("Tickets Resarves avec succes!")
      },error => {
        console.log(error);
      });
  }
}
