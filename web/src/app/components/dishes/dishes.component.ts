import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestaurantService} from "../../services/restaurant/restaurant.service";
import {DishService} from "../../services/dish/dish.service";
import {DishesService} from "../../services/dishes/dishes.service";
import {Restaurant} from "../../models/Restaurant";
import {DishResponse} from "../../models/DishResponse";
import {ActivatedRoute, Router} from "@angular/router";
import {Dish} from "../../models/Dish";
import {SharedService} from "../../services/shared.service";

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
  constructor(private dishesService:DishesService,private route: ActivatedRoute,private sharedService: SharedService,private router: Router) { }
  public searchedText: string;
  public index:number=1;
  ngOnDestroy(): void {
  }
  ngOnInit(): void {
    this.sharedService.currentData.subscribe(data => {
      this.searchedText=data;
    });
    let name="";
    this.route.paramMap
      .subscribe((params: any) => {
        name = params.get('restaurantName');
        this.page=params.get('p');
        this.index=1;
        this.url="Restaurants/"+name+'/'+ this.index;
      });
    this.getAllDishes(name,this.page)
  }
  getAllDishes(name:string,page:number){
    this.dishesService.getDishes(name,page)
      .subscribe((response:DishResponse)=>this.dishes=response.content);
  }

  receiveData($event: any) {

  }

  NextPage() {
    let num=this.index++;
    let modifiedString = this.url.slice(0,-1);
    modifiedString=modifiedString+num;
    console.log(modifiedString)
    this.router.navigate([modifiedString]);
  }

  PrevPage() {
    let num=this.index--;
    let modifiedString = this.url.slice(0,-1);
    modifiedString=modifiedString+num;
    this.router.navigate([modifiedString]);
  }
}
