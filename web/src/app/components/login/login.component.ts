import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../services/account/account.service";
import {Credentials} from "../../models/credentials";
import {Router} from "@angular/router";
import {ApiError} from "../../models/apiError";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm : FormGroup = this.fb.group({
    login: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  invalidValuesInAnyControl: boolean = false;
  errorOccurredDuringLogin: boolean = false;
  errorMessage: string = "";

  constructor(private fb: FormBuilder, private accountService: AccountService, private router: Router) {
  }

  login() {
    this.invalidValuesInAnyControl = false;
    this.errorMessage = "";
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      if (control?.errors) {
        this.invalidValuesInAnyControl = true;
        console.log(key, control.errors);
        return;
      }
    });
    if (this.invalidValuesInAnyControl) {
      return;
    }
    let credentials: Credentials = {
      login: this.loginForm.controls['login'].value,
      password: this.loginForm.controls['password'].value
    }
    this.accountService.login(credentials).subscribe({
      next: (loggedSuccessfully) => {
        if(loggedSuccessfully){
          this.router.navigate(['/Restaurants']).then();
          return;
        }
        this.errorOccurredDuringLogin = true;
      },
      error: (err: HttpErrorResponse) => {
        let apiError = err.error;
        this.errorMessage = apiError.message;
        this.errorOccurredDuringLogin = true;
      }
    });
    if (this.errorOccurredDuringLogin && this.errorMessage === "") {
      this.errorMessage = "Wystąpił błąd podczas logowania";
    }
  }
}
