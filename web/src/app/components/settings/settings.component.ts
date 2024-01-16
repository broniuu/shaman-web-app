import {Component, OnDestroy, OnInit} from '@angular/core';
import {AccountService} from "../../services/account/account.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../models/apiError";
import {ToastService} from "../../services/toast/toast.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CreditCardValidators} from "angular-cc-library";
import {User} from "../../models/user";
import {Subscription} from "rxjs";
import {EditUser} from "../../models/editUser";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit, OnDestroy {

  user: User;
  errorOccurredWhenEditing = false;
  errorMessage = "";
  subscription: Subscription;

  constructor(
    private accountService: AccountService,
    private toastService: ToastService,
    private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.subscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.router.navigated = false;
      }
    });
  }

  ngOnInit(): void {
    this.accountService.getLoggedUser().subscribe({
        next: value => this.user = value,
        error: (err: HttpErrorResponse) => {
          const apiError: ApiError = err.error;
          this.toastService.showDanger(apiError?.message ?? "Wystąpił błąd podczas wczytywania danych użytkownika")
        }
      }
    )
  }

  editAccount(userToEdit: EditUser) {
    let loginChanged = userToEdit.login !== this.accountService.getLogin()
    if (loginChanged){
      let confirmationResult = confirm("Po edycji swoich danych będziesz musiał zalogować się ponownie")
      if (!confirmationResult) {
        return;
      }
    }
    this.accountService.updateUser(userToEdit).subscribe({
      next: () => {
        if(loginChanged){
          this.accountService.logout();
          this.router.navigate(['login']);
          return;
        }
        this.router.navigate(['settings']).then(r =>
          this.toastService.showSuccess("Pomylnie edytowany dane użytkownika"));
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);
        let apiError: ApiError = err.error;
        this.errorMessage = apiError.message;
        this.errorOccurredWhenEditing = true;
        this.toastService.showDanger("Wystąpił błąd podczas edycji danych użytkownika");
      }
    });
  }

  deleteAccount() {
    let deleteConfirmed = confirm('Czy na pewno chcesz usunąć konto?');
    if (!deleteConfirmed) {
      return;
    }
    this.accountService.deleteLoggedUser().subscribe({
      next: () => {
        this.router.navigate(['Restaurants']).then(() =>
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

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
