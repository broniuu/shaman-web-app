import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Dish} from "../../../models/Dish";
import {ActivatedRoute, Router} from "@angular/router";
import {auto} from "@popperjs/core";
import {Colors} from "../../Colors";

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
  @Input() dish: Dish
  public url = "http://localhost:4200/Restaurant/"
  isLoggedIn: any;

  constructor(private route: ActivatedRoute, private router: Router) {
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
        this.url = "http:/localhost:4200/Restaurant/" + name + '/1';
      });
  }


  addItemToCasket(dishId: any) {


    this.count=0;
    this.blocekd=true;
  }

  protected readonly auto = auto;
  protected readonly window = window;
  protected readonly Colors = Colors;

  GotoDish(name: string) {

    this.router.navigate([this.url + name]);

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
