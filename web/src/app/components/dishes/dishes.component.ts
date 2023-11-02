import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestaurantService} from "../../services/restaurant/restaurant.service";
import {DishService} from "../../services/dish/dish.service";
import {DishesService} from "../../services/dishes/dishes.service";
import {Restaurant} from "../../models/Restaurant";
import {DishResponse} from "../../models/DishResponse";
import {ActivatedRoute} from "@angular/router";
import {Dish} from "../../models/Dish";

@Component({
  selector: 'app-dishes',
  templateUrl: './dishes.component.html',
  styleUrls: ['./dishes.component.css']
})
export class DishesComponent implements OnInit,OnDestroy{
  public dishes: Dish[];
  public page:number;
  public url:string;
  public name:string;
  constructor(private dishesService:DishesService,private route: ActivatedRoute) { }

  ngOnDestroy(): void {
  }
  ngOnInit(): void {
    let name="";
    this.route.paramMap
      .subscribe((params: any) => {
        name = params.get('restaurantName');
        this.page=params.get('p');
        this.url="http://localhost:4200/Restaurant/"+name+'/'+1;
      });
    this.getAllDishes(name,this.page)
  }
  getAllDishes(name:string,page:number){
    this.dishesService.getDishes(name,page)
      .subscribe((response:DishResponse)=>this.dishes=response.content);
  }
}
