import { Injectable } from '@angular/core';
import { Restaurant } from '../../models/restaurant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class RestaurantService {
  private apiUrl = 'http://localhost:8082/restaurants';

  getRestaurants():Observable<Restaurant[]>  {
    const url = `${this.apiUrl}`;
    return this.http.get<Restaurant[]>(url);
  }
  getRestaurant(id: string): Observable<Restaurant> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Restaurant>(url,{responseType: 'json'});
  }

  constructor(private http: HttpClient) { }
}
