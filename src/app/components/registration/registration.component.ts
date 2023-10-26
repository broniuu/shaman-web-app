import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";

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

  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) =>
      control?.value === control?.parent?.get(matchTo)?.value
        ? null : {isMatching: true}
  }

  submit(): void {
    console.log(this.registerForm.controls['password'].errors);
    console.log(this.registerForm.controls['confirmPassword'].errors);
    console.log(this.registerForm.value);
  }

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.registerForm.controls['password'].valueChanges.subscribe(() => {
      this.registerForm.controls['confirmPassword'].updateValueAndValidity();
    });
  }
}
