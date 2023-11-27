import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Dish} from "../../models/dish";
import { HttpClient } from '@angular/common/http';
import {DishResponse} from '../../models/dishResponse';
@Injectable({
  providedIn: 'root'
})
export class DishService {

  constructor(private http: HttpClient) { }
  getDish(restaurantName:string,dishName:string):Observable<Dish>  {
    const url = 'http://localhost:8082/restaurants/'+restaurantName+'/dishes/'+dishName;
    return this.http.get<Dish>(url);
  }
}
