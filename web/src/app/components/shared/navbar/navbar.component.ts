import {Component, Input} from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import {NavbarComunicationService} from "../../../services/navbar/navbar-comunication.service";
import {Colors} from "../../Colors";

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private router: Router,
              private sharedService: NavbarComunicationService,
              private route: ActivatedRoute ) {
    this.sharedService.functionCalled$.subscribe(() => {
      this.loggedIn();
    });
    this.sharedService.barVisibility$.subscribe(() => {
      this.ShowSearchBar()
    });
    this.sharedService.barInVisibility$.subscribe(() => {
      this.HideSearchBar()
    });
  }
  @Input() searchString?: string="";
  logged=false;
  searchBarVisibility=true;

  sendString(): void {
    if(this.searchString){
      this.sharedService.setSearch(this.searchString.valueOf());
      this.sharedService.filterFunction();
    }else{
      this.sharedService.setSearch("");
      this.sharedService.filterFunction();
    }
  }
  public ShowSearchBar(){
    this.searchBarVisibility=true;
  }
  public HideSearchBar(){
    this.searchBarVisibility=false;
  }
  Account() {
    this.router.navigate(['signup']);
  }
  GotoHome() {
    this.router.navigate(['home']);
  }

  Login() {
    this.router.navigate(['login']);
  }
  loggedIn(): void {
    this.logged=true;
  }
  LogOut():void{
      this.router.navigate(['home']);
  }
  GotoFavorite() {
    this.router.navigate(['favorite']);
  }

  GoToSettings() {
    this.router.navigate(['settings']);

  }

  GotoRestaurants() {
    this.router.navigate(['Restaurants']);
  }

  protected readonly Colors = Colors;
}
