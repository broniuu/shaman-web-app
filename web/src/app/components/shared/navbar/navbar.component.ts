import {Component, Input} from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import {NavbarComunicationService} from "../../../services/navbar/navbar-comunication.service";
import {Colors} from "../../Colors";
import {AccountService} from "../../../services/account/account.service";
import {ToastService} from "../../../services/toast/toast.service";

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private router: Router,
              private sharedService: NavbarComunicationService,
              private accountService: AccountService,
              private toastService: ToastService) {
    this.router.events.subscribe(val => {
      this.searchBarVisibility = router.url.toLowerCase().startsWith('/restaurants') || router.url === '/';
    });
    let username = accountService.getLogin();
    this.loggedUserName = username ?? "";
    this.logged = !!username;
    this.sharedService.loggedUserName.subscribe((val) => {
      this.loggedUserName = val;
      this.logged = val !== "";
    })
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
  loggedUserName = "";

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
    this.router.navigate(['register']);
  }
  GotoHome() {
    this.router.navigate(['/Restaurants']);
  }

  Login() {
    this.router.navigate(['login']);
  }
  LogOut():void{
      this.accountService.logout();
      this.loggedUserName = "";
      this.logged = false;
      this.router.navigate(['Restaurants']).then(() =>
      this.toastService.showSuccess("Pomyślnie wylogowano"));
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

  goToCart() {
    this.router.navigate(['cart']);
  }

  protected readonly Colors = Colors;
}
