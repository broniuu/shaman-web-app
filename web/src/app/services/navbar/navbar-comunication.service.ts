import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavbarComunicationService {
  private functionCalledSource = new Subject<void>();
  private  barVisibleSource = new Subject<void>();
  private  barInvisibleSource = new Subject<void>();
  private  filterSource = new Subject<void>();
  loggedUserName: Subject<string> = new Subject();

  loggedUserChange(name: string) {
    this.loggedUserName.next(name);
  }

  private stringSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');

  functionCalled$ = this.functionCalledSource.asObservable();
  barVisibility$ = this.barVisibleSource.asObservable();
  barInVisibility$ = this.barInvisibleSource.asObservable();
  filterSearch$ = this.filterSource.asObservable();
  filterFunction(): void {
    this.filterSource.next();
  }
  getSearch(): Observable<string> {
    return this.stringSubject.asObservable();
  }
  setSearch(value: string): void {
    this.stringSubject.next(value);
  }
  callFunction(): void {
    this.functionCalledSource.next();
  }
  ShowBar(): void {
    this.barVisibleSource.next();
  }
  HideBar() {
    this.barInvisibleSource.next();
  }
}
