import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {LocalStorageService} from "../local-storage/local-storage.service";
import {CartItem} from "../../models/cartItem";
import {CartItemResponse} from "../../models/cartItemResponse";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  readonly apiUrl: string = environment.apiUrl;
  constructor(private http: HttpClient, private storageService: LocalStorageService) { }

  getCart() {
    const login = this.storageService.getLogin();
    const url = `${this.apiUrl}/${login}/usercart?pageSize=1000&pageNumber=0`;
    return this.http.get<CartItemResponse>(url);
  }

  addToCart() {

  }

}
