import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Restaurant} from "../../models/Restaurant";
import {RestaurantService} from "../../services/restaurant/restaurant.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Observable, Subscription} from "rxjs";
import {NavbarComunicationService} from "../../services/navbar/navbar-comunication.service";

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit,OnDestroy{
 public restaurants: Restaurant[];
 public response:any;
  private subscription: Subscription;
  @Input() filterText: string = '';

  constructor(private restaurantService: RestaurantService, private navbarCommunication:NavbarComunicationService) {}

  ngOnInit(): void {
    this.subscription = this.navbarCommunication.getSearch().subscribe((value) => {
      this.filterText = value;
    });
     this.getAllRestaurants();
  }

getAllRestaurants(){
    this.restaurantService.getRestaurants()
      .subscribe((response:Restaurant[])=>this.restaurants=response);
}
  restaurantId: string;
  name: string;
  imageUrl: string;
  backgroundImageUrl: string;
  score: number;
  street: string;
  houseNumber: string;
  location: string;
    ngOnDestroy(): void {
    }
}
