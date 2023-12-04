import {Dish} from "./dish";
import {Pageable} from "./pagable";
import {Sort} from "./sort";

export interface PageableResponse<T> {
  content: T[];
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
