import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {LocalStorageService} from "../local-storage/local-storage.service";
import {CartItem} from "../../models/cartItem";
import {CartItemResponse} from "../../models/cartItemResponse";
import {CheckoutModel} from "../../models/checkoutModel";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  readonly apiUrl: string = environment.apiUrl;
  constructor(private http: HttpClient, private storageService: LocalStorageService) { }

  getCart() {
    const login = this.storageService.getLogin();
    const url = `${this.apiUrl}/${login}/usercart`;
    return this.http.get<CartItemResponse>(url);
  }

  saveToCart(dishId: string, count: number) {
    const login = this.storageService.getLogin();
    const url = `${this.apiUrl}/${login}/usercart/${dishId}/save/${count}`;
    return this.http.post<CartItem>(url,{});
  }

  remove(cartItemId: string) {
    const login = this.storageService.getLogin();
    const url = `${this.apiUrl}/${login}/usercart/${cartItemId}/delete`;
    return this.http.delete<CartItem>(url);
  }

  checkout(checkout: CheckoutModel): Observable<Blob> {
    const login = this.storageService.getLogin();
    const url = `${this.apiUrl}/${login}/usercart/checkout`;
    return this.http.post(url, checkout, { responseType: 'blob' });
  }

}
