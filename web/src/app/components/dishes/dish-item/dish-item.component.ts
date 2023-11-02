import {Component, Input, OnInit} from '@angular/core';
import {Dish} from "../../../models/Dish";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-dish-item',
  templateUrl: './dish-item.component.html',
  styleUrls: ['./dish-item.component.css']
})
export class DishItemComponent implements OnInit {
  @Input() dish :Dish
  public url="http://localhost:4200/Restaurant/"
  constructor(private route: ActivatedRoute,private router: Router) { }
  onButtonClick(name:string) {
    console.log("click")
    this.router.navigate([this.url+name]);
  }
  ngOnInit(): void {
    this.route.paramMap
      .subscribe((params: any) => {
        let name = params.get('restaurantName');
        this.url="http:/localhost:4200/Restaurant/"+name+'/1';
      });
  }
}
