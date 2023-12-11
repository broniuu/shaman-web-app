import { Injectable } from '@angular/core';
import {AccountService} from "../account/account.service";
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class GuardService {

  constructor(private authService: AccountService, public router: Router) {
  }
  canActivate(
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/Restaurants');
      return false;
    } else {
      return true;
    }
  }
}
