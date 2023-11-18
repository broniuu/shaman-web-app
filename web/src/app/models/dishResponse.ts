import {Dish} from "./dish";
import {Pageable} from "./pagable";
import {Sort} from "./sort";
import {PageableResponse} from "./pageableResponse";

export interface DishResponse extends PageableResponse<Dish>{
}
