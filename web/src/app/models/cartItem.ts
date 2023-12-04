import {Dish} from "./dish";

export interface CartItem {
  cartItemId: string,
  countOfDish: number,
  cartOwner: CartOwner,
  dish: Dish
}

export interface CartOwner {
  userId: string;
  login: string;
}
