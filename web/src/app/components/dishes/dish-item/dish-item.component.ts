import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Dish} from "../../../models/dish";
import {ActivatedRoute, Router} from "@angular/router";
import {auto} from "@popperjs/core";
import {Colors} from "../../Colors";
import {CartService} from "../../../services/cart/cart.service";
import {ToastService} from "../../../services/toast/toast.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-dish-item',
  templateUrl: './dish-item.component.html',
  styleUrls: ['./dish-item.component.css']
})
export class DishItemComponent implements OnInit {
  public screenWidth: number;
  public screenHeight: number;
  public blocekd:boolean=true;
  public count:number=0;
  restaurantName:string=""
  @Input() dish: Dish
  public url = "http://localhost:4200/Restaurant/"
  isLoggedIn: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cartService: CartService,
    private toastService: ToastService) {
  }
  @HostListener('window:resize', ['$event'])
  getScreenSize(event?: Event) {
    this.screenWidth = window.innerWidth / 3;
    this.screenHeight = this.screenWidth * 0.7;
  }

  ngOnInit(): void {
    this.screenWidth = window.innerWidth / 3;
    this.screenHeight = this.screenWidth * 0.7;
    this.route.paramMap
      .subscribe((params: any) => {
        let name = params.get('restaurantName');
        this.restaurantName=name;
        this.url = "http:/localhost:4200/Restaurants/" + name + '/1';
      });
  }


  addToCart(dishId: any) {
    this.cartService.saveToCart(dishId, this.count).subscribe({
      next: () => {
        this.toastService.showSuccess("Dodano do koszyka");
        this.count=0;
        this.blocekd=true;
      },
      error: (err: HttpErrorResponse) => {
        this.toastService.showDanger(err.error.message);
      }
    });
  }

  protected readonly auto = auto;
  protected readonly window = window;
  protected readonly Colors = Colors;

  GotoDish(name: string) {
    this.router.navigate(["Dishes/" + this.restaurantName +"/"+name ]);
  }

  Increase() {
  if(this.count > 98 ){
    return;
  }
  this.count++;
    this.checkAndUnlock()
  }

  private checkAndUnlock() {
    if(this.count>0){
      this.blocekd=false;
    }else{
      this.blocekd=true;
    }
    console.log(this.blocekd)
  }

  Decrease() {
    if(this.count < 1 ){
      return;
    }
    this.count--;
    this.checkAndUnlock()
  }

}
