import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";
import {AccountService} from "../../services/account/account.service";
import {User} from "../../models/user";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastService} from "../../services/toast/toast.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})

export class RegistrationComponent implements OnInit{
  registerForm: FormGroup = this.fb.group({
    login: ['', [Validators.required]],
    password: ['', [
      Validators.required,
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/gm)]],
    confirmPassword: ['', [Validators.required, this.matchValues('password')]],
    name: ['', [Validators.required]],
    surname: ['', [Validators.required]],
    address: ['', [Validators.required]],
    debitCardNumber: ['', [CreditCardValidators.validateCCNumber]],
    expireDate: ['', [CreditCardValidators.validateExpDate]],
    cvv: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(4)]],
    email: ['', [Validators.email, Validators.required]]
  });

  invalidValuesInAnyControl: boolean = false;
  errorOccuredDuringAddingUser: boolean = false;
  errorMessage: string = "";
  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) =>
      control?.value === control?.parent?.get(matchTo)?.value
        ? null : {isMatching: true}
  }

  submit(): void {
    this.invalidValuesInAnyControl = false;
    this.errorMessage = "";
    Object.keys(this.registerForm.controls).forEach(key => {
      const control = this.registerForm.get(key);
      if (control?.errors) {
        this.invalidValuesInAnyControl = true;
        console.log(key, control.errors);
        return;
      }
    });
    if (this.invalidValuesInAnyControl) {
      return;
    }
    let user: User = {
      login: this.registerForm.controls['login'].value,
      password: this.registerForm.controls['password'].value,
      name: this.registerForm.controls['name'].value,
      surname: this.registerForm.controls['surname'].value,
      address: this.registerForm.controls['address'].value,
      debitCardNumber: this.registerForm.controls['debitCardNumber'].value,
      expireDate: this.registerForm.controls['expireDate'].value,
      cvv: this.registerForm.controls['cvv'].value,
      email: this.registerForm.controls['email'].value
    };
    this.accountService.register(user).subscribe({
      next: () => this.router.navigate(['login'])
        .then(() => this.toastService.showSuccess('Utworzono konto')),
      error: (err: HttpErrorResponse) => {
        let apiError = err.error;
        this.errorMessage = apiError.message;
        this.errorOccuredDuringAddingUser = true;
      }
    });
    if (this.errorOccuredDuringAddingUser && this.errorMessage === "") {
      this.errorMessage = "Wystąpił błąd podczas dodawania użytkownika";
    }
  }

  constructor(private fb: FormBuilder, private accountService: AccountService, private router: Router,
              private toastService: ToastService) {
  }

  ngOnInit(): void {
    this.registerForm.controls['password'].valueChanges.subscribe(() => {
      this.registerForm.controls['confirmPassword'].updateValueAndValidity();
    });
  }
}
