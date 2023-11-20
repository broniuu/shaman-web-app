import { Injectable } from '@angular/core';
import {map, Observable} from "rxjs";
import {Restaurant} from "../../models/restaurant";
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user";
import {Credentials} from "../../models/credentials";
import {TokenContainer} from "../../models/tokenContainer";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8082/user';

  constructor(private http: HttpClient) { }

  register(userToRegister: User): Observable<User>  {
    const url = `${this.apiUrl}/registration`;
    return this.http.post<User>(url, userToRegister);
  }
  isLoggedIn() {
    const jwtHelper = new JwtHelperService();
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }
    return !(jwtHelper.isTokenExpired(token));
  }
  login(credentials: Credentials): Observable<boolean> {
    const url = `${this.apiUrl}/login`;
    return this.http.post<User>(url, credentials).pipe(
      map((result: TokenContainer | any) => {
        if (result && result.value) {
          localStorage.setItem('token', result.value);
          return true;
        }
        return false;
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
