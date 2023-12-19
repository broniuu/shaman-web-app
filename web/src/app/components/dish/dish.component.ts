import {Component, Input, OnInit} from '@angular/core';
import {Dish} from "../../models/dish";
import {Colors} from "../Colors";
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../services/cart/cart.service";
import {ToastService} from "../../services/toast/toast.service";
import {DishResponse} from "../../models/dishResponse";
import {DishService} from "../../services/dish/dish.service";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html',
  styleUrls: ['./dish.component.css']
})
export class DishComponent implements OnInit {
  public screenWidth: number;
  public screenHeight: number;
  public blocked:boolean=true;
  public count:number=0;
  @Input() dish: Dish
  public url = "http://localhost:4200/Dishes/"
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dishService:DishService,
    private cartService: CartService,
    private toastService: ToastService) {
  }

  Decrease() {
    if(this.count < 1 ){
      return;
    }
    this.count--;
    this.checkAndUnlock()
  }
  private checkAndUnlock() {
    if(this.count>0){
      this.blocked=false;
    }else{
      this.blocked=true;
    }
  }
  Increase() {
    if(this.count > 98 ){
      return;
    }
    this.count++;
    this.checkAndUnlock()
  }
  addToCart(dishId: any) {

    this.cartService.saveToCart(dishId, this.count).subscribe({
      next: () => {
        this.toastService.showSuccess("Dodano do koszyka");
        this.count=0;
        this.blocked=true;
      },
      error: (err: HttpErrorResponse) => {
        this.toastService.showDanger(err.error.message);
      }
    });
  } protected readonly Colors = Colors;

  ngOnInit(): void {
    this.screenWidth = window.innerWidth / 2;
    this.screenHeight = this.screenWidth * 0.7;
    this.route.paramMap
      .subscribe((params: any) => {
        let rname = params.get('restaurantName');
        let dname = params.get('dishName');
        this.url = "http:/localhost:4200/Dishes/"+rname +'/' + dname ;
        this.getDish(rname,dname)
      });
  }
  getDish(restaurant:string,dish:string){
    this.dishService.getDish(restaurant,dish)
      .subscribe((response:Dish)=>this.dish=response);
  }
}
