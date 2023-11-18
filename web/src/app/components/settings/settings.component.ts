import {Component} from '@angular/core';
import {AccountService} from "../../services/account/account.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../models/apiError";
import {ToastService} from "../../services/toast/toast.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
  constructor(private accountService: AccountService, private toastService: ToastService, private router: Router) {

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
