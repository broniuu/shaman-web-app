import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart/cart.service";
import {CartItem} from "../../models/cartItem";
import {ToastService} from "../../services/toast/toast.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../models/apiError";
import {Colors} from "../Colors";
import {CheckoutModel} from "../../models/checkoutModel";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  cartItems: CartItem[];
  checkoutModel: CheckoutModel = {
    discountCode: "",
    note: "",
    delivery: true
  }

  constructor(private cartService: CartService, private toastService: ToastService) {
  }

  ngOnInit(): void {
    this.getCart();
  }

  getCart() {
    this.cartService.getCart().subscribe({
      next: cart => this.cartItems = cart.content,
      error: (err: HttpErrorResponse) => {
        const apiError: ApiError = err.error;
        this.toastService.showDanger(apiError.message);
      }
    });
  }

  saveItemCount(dishId: string, count: number) {
    this.cartService.saveToCart(dishId, count).subscribe({
      error: (err: HttpErrorResponse) => {
        this.toastService.showDanger(err.error.message);
      }
    });
  }

  increaseItemCount(cartItem : CartItem) {
    const index = this.cartItems.findIndex(i => i.cartItemId === cartItem.cartItemId);
    if (index !== -1) {
      this.cartItems[index].countOfDish += 1;
      this.saveItemCount(cartItem.dish.dishId, cartItem.countOfDish);
    }
  }

  decreaseItemCount(cartItem : CartItem) {
    const index = this.cartItems.findIndex(i => i.cartItemId === cartItem.cartItemId);
    if (index !== -1) {
      this.cartItems[index].countOfDish -= 1;
      this.saveItemCount(cartItem.dish.dishId, cartItem.countOfDish);

    }
  }

  removeFromCart(cartItemId: string) {
    this.cartService.remove(cartItemId).subscribe({
      next: (cartItem) => {
        this.cartItems = this.cartItems.filter(x => x.cartItemId !== cartItem.cartItemId);
        this.toastService.showSuccess("Usunięteo z koszyka");
      },
      error: (err: HttpErrorResponse) => {
        const apiError: ApiError = err.error;
        this.toastService.showDanger(apiError.message);
      }
    });
  }

  getTotalPrice() {
    return this.cartItems
      ?.map(ci => ci.countOfDish * ci.dish.price)
      ?.reduce((agg, p) => agg + p) + ' zł';
  }

  getPrice(item: CartItem){
    return (item.countOfDish * item.dish.price) + ' zł';
  }

  buy() {
    console.log(this.checkoutModel);
    this.cartService.checkout(this.checkoutModel).subscribe({
      next: blob => {
        const pdfUrl = URL.createObjectURL(blob);
        window.open(pdfUrl);
        this.getCart();
      },
      error: (err: HttpErrorResponse) => {
        if (err.error instanceof Blob) {
          const reader = new FileReader();
          reader.onload = e => {
            const jsonResponse: ApiError = JSON.parse((e.target as FileReader).result as string);
            this.toastService.showDanger(jsonResponse.message);
          };
          reader.readAsText(err.error);
          return;
        }
        this.toastService.showDanger("Wystąpił błąd");
      }
    })
  }

  protected readonly Colors = Colors;
}
