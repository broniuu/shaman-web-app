import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Dish} from "../../models/Dish";
import { HttpClient } from '@angular/common/http';
import {DishResponse} from '../../models/DishResponse';
@Injectable({
  providedIn: 'root'
})
export class DishesService {
  getDishes(restaurantName:string,page:number):Observable<DishResponse>  {
    const url = 'http://localhost:8082/restaurants/'+restaurantName+'/dishes?p='+page;
    return this.http.get<DishResponse>(url);
  }
  getDish(restaurantName:string,dishName:string):Observable<Dish>  {
    const url = 'http://localhost:8082/restaurants/'+restaurantName+'/dishes/'+dishName;
    return this.http.get<Dish>(url);
  }
  constructor(private http: HttpClient) { }
}
