import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../../models/Restaurant";
import {ActivatedRoute, Router} from "@angular/router";
import {Colors} from "../../Colors";

@Component({
  selector: 'app-restaurant-item',
  templateUrl: './restaurant-item.component.html',
  styleUrls: ['./restaurant-item.component.css']
})
export class RestaurantItemComponent implements OnInit{
  @Input() restaurant: Restaurant;
  public screenWidth: number;
  public screenHeight: number;
  isLoading: boolean = true;

  getScreenSize(event?: Event) {
    this.screenWidth = window.innerWidth / 3;
    this.screenHeight = this.screenWidth * 0.7;

  }
  constructor(private route: ActivatedRoute, private router: Router) {
  }
  ngOnInit(): void {
    this.screenWidth = window.innerWidth / 3;
  }

  GoToDishes(name: string) {
    this.router.navigate([`/Restaurants/${name}/1`]);
  }

  protected readonly Colors = Colors;
}
