import {Component, OnInit} from '@angular/core';
import {AccountService} from "../../services/account/account.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../models/apiError";
import {ToastService} from "../../services/toast/toast.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";
import {User} from "../../models/user";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit{

  user: User;
  errorOccurredWhenEditing = false;
  errorMessage = "";
  constructor(
    private accountService: AccountService,
    private toastService: ToastService,
    private router: Router) {

  }

  ngOnInit(): void {
    this.accountService.getLoggedUser().subscribe({
      next: value => this.user = value,
      error: (err: HttpErrorResponse) => {
        const apiError: ApiError = err.error ;
        this.toastService.showDanger(apiError?.message ?? "Wystąpił błąd podczas wczytywania danych użytkownika")
      }
      }
    )
  }

  editAccount(userToEdit: User) {
    this.errorMessage = "asdasd";
    this.errorOccurredWhenEditing = true;
    this.toastService.showSuccess('Edit test');
  }
  deleteAccount() {
    let deleteConfirmed = confirm('Czy na pewno chcesz usunąć konto?');
    if (!deleteConfirmed) {
      return;
    }
    this.accountService.deleteLoggedUser().subscribe({
      next: () => {
        this.router.navigate(['/']).then(() =>
          this.toastService.showSuccess('Konto zostało usunięte')
        );
      },
      error: (response: HttpErrorResponse) => {
        console.log(response.message)
        let error: ApiError = response.error
        this.toastService.showDanger(error.message);
      }
    });
  }
}
