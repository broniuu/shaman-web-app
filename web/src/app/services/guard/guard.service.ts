import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AccountService} from "../account/account.service";

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
      this.router.navigateByUrl('/');
      return false;
    } else {
      return true;
    }
  }
}

