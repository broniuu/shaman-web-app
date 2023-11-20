import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";
import {User} from "../../models/user";
import {HttpErrorResponse} from "@angular/common/http";
import {AccountService} from "../../services/account/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit, OnChanges {
  @Input() user: User = {
    login: '',
    password: '',
    name: '',
    surname: '',
    address: '',
    debitCardNumber: '',
    expireDate: '',
    cvv: '',
    email: ''
  };

  @Output() submitEvent = new EventEmitter<User>;

  @Input() errorMessage: string = "";
  @Input() errorOccurred: boolean = false;
  @Input() submitButtonText = "Submit";

  invalidValuesInAnyControl: boolean = false;

  userForm: FormGroup = this.fb.group({
    login: [this.user.login, [Validators.required]],
    password: ['', [
      Validators.required,
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/gm)]],
    confirmPassword: ['', [Validators.required, this.matchValues('password')]],
    name: [this.user.name, [Validators.required]],
    surname: [this.user.surname, [Validators.required]],
    address: [this.user.address, [Validators.required]],
    debitCardNumber: [this.user.debitCardNumber, [CreditCardValidators.validateCCNumber]],
    expireDate: [this.user.expireDate, [CreditCardValidators.validateExpDate]],
    cvv: [this.user.cvv, [Validators.required, Validators.minLength(3), Validators.maxLength(4)]],
    email: [this.user.email, [Validators.email, Validators.required]]
  });
  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) =>
      control?.value === control?.parent?.get(matchTo)?.value
        ? null : {isMatching: true}
  }

  submit(): void {
    this.invalidValuesInAnyControl = false;
    this.errorMessage = "";
    Object.keys(this.userForm.controls).forEach(key => {
      const control = this.userForm.get(key);
      if (control?.errors) {
        this.invalidValuesInAnyControl = true;
        console.log(key, control.errors);
        return;
      }
    });
    if (this.invalidValuesInAnyControl) {
      this.invalidValuesInAnyControl = false;
      return;
    }
    this.user = {
      login: this.userForm.controls['login'].value,
      password: this.userForm.controls['password'].value,
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      address: this.userForm.controls['address'].value,
      debitCardNumber: this.userForm.controls['debitCardNumber'].value,
      expireDate: this.userForm.controls['expireDate'].value,
      cvv: this.userForm.controls['cvv'].value,
      email: this.userForm.controls['email'].value
    };
    this.submitEvent.emit(this.user);
  }

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
      this.userForm.controls['password'].valueChanges.subscribe(() => {
      this.userForm.controls['confirmPassword'].updateValueAndValidity();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.userForm.controls['login'].setValue(this.user.login);
    this.userForm.controls['name'].setValue(this.user.name);
    this.userForm.controls['surname'].setValue(this.user.surname);
    this.userForm.controls['address'].setValue(this.user.address);
    this.userForm.controls['debitCardNumber'].setValue(this.user.debitCardNumber);
    this.userForm.controls['expireDate'].setValue(this.user.expireDate);
    this.userForm.controls['cvv'].setValue(this.user.cvv);
    this.userForm.controls['email'].setValue(this.user.email);
  }
}
