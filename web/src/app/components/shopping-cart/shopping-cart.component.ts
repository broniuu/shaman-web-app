import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart/cart.service";
import {CartItem} from "../../models/cartItem";
import {ToastService} from "../../services/toast/toast.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../models/apiError";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit{

  constructor(private cartService: CartService, private toastService: ToastService) {
  }

  cartItems: CartItem[];
  ngOnInit(): void {
    this.cartService.getCart().subscribe({
      next: cart => this.cartItems = cart.content,
      error: (err: HttpErrorResponse) => {
        const apiError: ApiError = err.error;
        this.toastService.showDanger(apiError.message);
      }
      });
  }

  protected readonly JSON = JSON;
}
