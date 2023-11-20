import { Injectable } from '@angular/core';
import {map, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../../models/user";
import {Credentials} from "../../models/credentials";
import {TokenContainer} from "../../models/tokenContainer";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8082';

  readonly localStorageTokenKey = 'token';
  readonly localStorageLoginKey = 'login'

  constructor(private http: HttpClient) { }

  register(userToRegister: User): Observable<User>  {
    const url = `${this.apiUrl}/user/registration`;
    return this.http.post<User>(url, userToRegister);
  }

  login(credentials: Credentials): Observable<boolean> {
    const url = `${this.apiUrl}/user/login`;
    return this.http.post<User>(url, credentials, {withCredentials: true}).pipe(
      map((result: TokenContainer | any) => {
        if (result && result.value) {
          localStorage.setItem(this.localStorageTokenKey, result.value);
          localStorage.setItem(this.localStorageLoginKey, credentials.login);
          return true;
        }
        return false;
      })
    );
  }

  deleteLoggedUser(): Observable<User> {
    let login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user/delete`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`);
    return this.http.delete<User>(url,{headers});
  }

  getLoggedUser(): Observable<User> {
    const login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user`;
    return this.http.get<User>(url);
  }

  updateUser(userToUpdate: User): Observable<User> {
    const login = this.getLogin();
    const url = `${this.apiUrl}/${login}/user/update`;
    return this.http.post<User>(url, userToUpdate);
  }

  getToken(): string | null {
    return localStorage.getItem(this.localStorageTokenKey);
  }

  getLogin(): string | null {
    return  localStorage.getItem(this.localStorageLoginKey);
  }

  logout() {
    localStorage.removeItem(this.localStorageLoginKey);
    localStorage.removeItem(this.localStorageTokenKey);
  }
}
