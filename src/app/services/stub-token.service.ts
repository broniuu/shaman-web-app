import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../models/credentials";
import {TokenContainer} from "../models/tokenContainer";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StubTokenService {

  constructor(private httpClient: HttpClient) { }

  public getAndSaveTokenToLocalStorage() :Observable<boolean> {
    let credentials = new Credentials();
    credentials.login = "jchocho";
    credentials.password = "haslo123";
    return this.httpClient.post('http://localhost:8082/user/login', credentials).pipe(
      map((result: TokenContainer | any) => {
        if (result && result.value) {
          localStorage.setItem('token', result.value);
          return true;
        }
        return false;
      })
    );
  }
}
