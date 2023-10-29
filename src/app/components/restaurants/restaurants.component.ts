import {Component, OnDestroy, OnInit} from '@angular/core';
import {Restaurant} from "../../models/Restaurant";
import {RestaurantService} from "../../services/restaurant/restaurant.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Observable} from "rxjs";

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit,OnDestroy{
 public restaurants: Restaurant[];
 public response:any;

  constructor(private restaurantService: RestaurantService) { }

  ngOnInit(): void {
    console.log("check+1")

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
