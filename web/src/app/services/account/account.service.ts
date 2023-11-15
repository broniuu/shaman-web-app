import { Injectable } from '@angular/core';
import {map, Observable} from "rxjs";
import {Restaurant} from "../../models/Restaurant";
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8082/user';

  constructor(private http: HttpClient) { }

  register(userToRegister: User): Observable<User>  {
    const url = `${this.apiUrl}`;
    return this.http.post<User>(url + "/registration", userToRegister);
  }
}
