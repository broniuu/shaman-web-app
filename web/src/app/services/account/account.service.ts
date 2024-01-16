import { Injectable } from '@angular/core';
import {map, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../../models/user";
import {Credentials} from "../../models/credentials";
import {TokenContainer} from "../../models/tokenContainer";
import {environment} from "../../../environments/environment.development";
import {JwtHelperService} from "@auth0/angular-jwt";
import {Role} from "../../models/role";
import {EditUser} from "../../models/editUser";
import {NavbarComunicationService} from "../navbar/navbar-comunication.service";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = environment.apiUrl;

  static readonly localStorageTokenKey = 'token';
  static readonly localStorageLoginKey = 'login'

  constructor(private http: HttpClient, private navbarCommunicationService: NavbarComunicationService) { }

  register(userToRegister: User): Observable<User>  {
    const url = `${this.apiUrl}/user/registration`;
    return this.http.post<User>(url, userToRegister);
  }
  isLoggedIn() {
    const jwtHelper = new JwtHelperService();
    const token = this.getToken();
    if (!token) {
      return false;
    }
    return !(jwtHelper.isTokenExpired(token));
  }
  login(credentials: Credentials): Observable<boolean> {
    const url = `${this.apiUrl}/user/login`;
    return this.http.post<User>(url, credentials, {withCredentials: true}).pipe(
      map((result: TokenContainer | any) => {
        if (result && result.value) {
          localStorage.setItem(AccountService.localStorageTokenKey, result.value);
          localStorage.setItem(AccountService.localStorageLoginKey, credentials.login);
          return true;
        }
        return false;
      })
    );
  }

  deleteLoggedUser(): Observable<User> {
    let login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user/delete`;
    return this.http.delete<User>(url);
  }

  getLoggedUser(): Observable<User> {
    const login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user`;
    return this.http.get<User>(url);
  }

  getLoggedUserRoles(): Observable<Role[]> {
    const login = this.getLogin();
    const url = `${this.apiUrl}/${login}/roles`;
    return this.http.get<Role[]>(url);
  }

  updateUser(userToUpdate: EditUser): Observable<EditUser> {
    const login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user/update`;
    return this.http.post<EditUser>(url, userToUpdate);
  }

  getToken(): string | null {
    return localStorage.getItem(AccountService.localStorageTokenKey);
  }

  getLogin(): string | null {
    return  localStorage.getItem(AccountService.localStorageLoginKey);
  }

  logout() {
    localStorage.removeItem(AccountService.localStorageLoginKey);
    localStorage.removeItem(AccountService.localStorageTokenKey);
    this.navbarCommunicationService.loggedUserChange('', []);
  }
}
