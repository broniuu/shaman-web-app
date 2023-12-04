import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Dish} from "../../models/dish";
import { HttpClient } from '@angular/common/http';
import {DishResponse} from '../../models/dishResponse';
@Injectable({
  providedIn: 'root'
})
export class DishesService {
  getDishes(restaurantName:string,page:number):Observable<DishResponse>  {
    const url = 'http://localhost:8082/restaurants/'+restaurantName+'/dishes?p='+page;
    return this.http.get<DishResponse>(url);
  }

  constructor(private http: HttpClient) { }
}
