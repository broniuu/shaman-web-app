import {Dish} from "./Dish";
import {Pageable} from "./Pagable";
import {Sort} from "./Sort";

export interface  DishResponse {
  content: Dish[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  numberOfElements: number;
  size: number;
  number: number;
  sort: Sort;
  empty: boolean;

}
