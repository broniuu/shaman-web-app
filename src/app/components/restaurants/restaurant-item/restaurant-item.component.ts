import {Component, Input} from '@angular/core';
import {Restaurant} from "../../../models/Restaurant";

@Component({
  selector: 'app-restaurant-item',
  templateUrl: './restaurant-item.component.html',
  styleUrls: ['./restaurant-item.component.css']
})
export class RestaurantItemComponent {
    @Input() restaurant: Restaurant;


}
