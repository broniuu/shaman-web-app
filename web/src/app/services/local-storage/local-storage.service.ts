import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  private readonly tokenStorageKey: string = 'token';
  private readonly loginStorageKey: string = 'login';
  constructor() { }

  getToken() {
    return localStorage.getItem(this.tokenStorageKey);
  }

  getLogin() {
   return localStorage.getItem(this.loginStorageKey);
  }

  setToken(token: string) {
    localStorage.setItem(this.tokenStorageKey, token);
  }

  setLogin(login: string) {
    localStorage.setItem(this.loginStorageKey, login);
  }
}
