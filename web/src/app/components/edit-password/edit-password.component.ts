import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ChangePassword} from "../../models/ChangePassword";
import {EditUser} from "../../models/editUser";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";
import {AccountService} from "../../services/account/account.service";

@Component({
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.css']
})
export class EditPasswordComponent implements OnChanges,OnInit {
  @Input() errorMessage: string = "";
  @Input() errorOccurred: boolean = false;
  @Input() submitButtonText = "Submit";
  @Output() submitEvent = new EventEmitter<ChangePassword>;
  invalidValuesInAnyControl: boolean = false;
  @Input() changePassword: ChangePassword = {
    login:'',
    password:'',
    oldPassword:'',
    repeatPassword:''
  };
  constructor(private fb: FormBuilder,private accountService:AccountService) {
  }
  changePasswordForm: FormGroup = this.fb.group({
    login: [this.changePassword.login, [Validators.required]],
    oldPassword: [this.changePassword.oldPassword, [Validators.required]],
    newPassword: [this.changePassword.password, [Validators.required]],
    repeatPassword: [this.changePassword.repeatPassword, [Validators.required]],
  });
  submit(): void {
    this.invalidValuesInAnyControl = false;
    this.errorMessage = "";
    if(this.changePasswordForm.controls['login'].value==''){
      this.invalidValuesInAnyControl = true;
      this.errorMessage="Nie podano loginu";
    }
    if(this.changePasswordForm.controls['newPassword'].value!== this.changePasswordForm.controls['repeatPassword'].value){
      this.invalidValuesInAnyControl = true;
      this.errorMessage="Hasła nie są zgodne";
    }
    console.log(this.errorMessage,this.changePasswordForm.controls['newPassword'].value,this.changePasswordForm.controls['repeatPassword'].value);
    Object.keys(this.changePasswordForm.controls).forEach((key:string) => {
      const control = this.changePasswordForm.get(key);
      if (control?.errors) {
        this.invalidValuesInAnyControl = true;
        console.log(key, control.errors);
        return;
      }
    });
    if (this.invalidValuesInAnyControl) {
      return;
    }
    this.invalidValuesInAnyControl = false;
    this.changePassword = {
      login: this.changePasswordForm.controls['login'].value,
      oldPassword: this.changePasswordForm.controls['oldPassword'].value,
      password: this.changePasswordForm.controls['newPassword'].value,
      repeatPassword: this.changePasswordForm.controls['repeatPassword'].value,

    };
    this.submitEvent.emit(this.changePassword);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.changePassword || !this.changePasswordForm) {
      return
    }

  }

  ngOnInit(): void {
    this.changePasswordForm.controls['login'].setValue(this.accountService.getLogin());

  }
}
