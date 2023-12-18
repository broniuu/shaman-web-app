import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {User} from "../../models/user";
import {UserShortInfo} from "../../models/editUser";
import {Role} from "../../models/role";
import {AccountService} from "../account/account.service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<UserShortInfo[]>(`${this.apiUrl}/users`);
  }

  getAllRoles() {
    return this.http.get<Role[]>(`${this.apiUrl}/roles`);
  }
}
