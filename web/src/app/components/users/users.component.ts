import {AfterContentInit, AfterViewInit, Component, NgModule, OnInit} from '@angular/core';
import {UsersService} from "../../services/users/users.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {EditUser, UserShortInfo} from "../../models/editUser";
import {ToastService} from "../../services/toast/toast.service";
import {HttpErrorResponse} from "@angular/common/http";
import {MatTableDataSource} from "@angular/material/table";
import {Role} from "../../models/role";
import {Router} from "@angular/router";
import {ApiError} from "../../models/apiError";
import {AccountService} from "../../services/account/account.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit, AfterContentInit{

  editUsers: EditUser[] = [];
  displayedColumns: string[] = ['name', 'surname', 'login', 'email', 'roles', 'action'];
  dataSource: MatTableDataSource<EditUser>;
  rolesData: Role[];
  currentUserLogin: string = '';

  constructor(private usersService: UsersService, private toastService: ToastService) {
    this.currentUserLogin = localStorage.getItem(AccountService.localStorageLoginKey) ?? '';
  }

  ngOnInit(): void {
    const editForm = (e: UserShortInfo) => new FormGroup({
      name: new FormControl(e.name, Validators.required),
      email: new FormControl(e.email, [Validators.required, Validators.email]),
      login: new FormControl(e.login, Validators.required),
      surname: new FormControl(e.surname, Validators.required),
      roles: new FormControl(e.roles.map<string>(x => x.roleId), Validators.required),
    });
    this.usersService.getAll().subscribe({
      next: (users) => {
        users.forEach(u =>
          this.editUsers.push({
            currentData: u,
            originalData: u,
            editable: false,
            validator: editForm(u)
          }))
        console.log(this.editUsers.slice())
        this.dataSource = new MatTableDataSource(this.editUsers.slice());
        console.log(this.dataSource);
      },
      error: (err: HttpErrorResponse) => {
        let apiError = err.error;
        let errorMessage = apiError.message;
        this.toastService.showDanger(errorMessage);
        console.log(err);
      }
    });
    this.usersService.getAllRoles().subscribe({
      next: (roles) => {
        this.rolesData = roles
        console.log('roles', roles)
      },
      error: (err: HttpErrorResponse) => {
        let apiError = err.error;
        let errorMessage = apiError.message;
        this.toastService.showDanger(errorMessage);
        console.log(err);
      }
    });
  }

  ngAfterContentInit(): void {
    for (const u of this.editUsers) {
      u.editable = false;
    }
  }

  public compareRoles(role1: Role, role2: Role) {
    return role1 && role2 ? role1.roleId === role2.roleId : false;
  }

  startEdit(row: EditUser) {
    row.editable = true;
  }

  confirmEditCreate(row: EditUser) {
    //Todo: fix
    if (!row.currentData) {
      console.error('currentData is null');
      return;
    }
    Object.keys(row.validator.controls).forEach(item => {
      let key: keyof UserShortInfo = item as any;
      if (!row.currentData) {
        return;
      }
      if (key === 'roles') {
        let roleIds: string[] = row.validator.controls[item].value;
        let mappedRoles: Role[] = []
        for (const id of roleIds) {
          let role = this.rolesData.find(r => r.roleId === id);
          if (role) {
            mappedRoles.push(role);
          }
        }
        row.currentData[key] = mappedRoles;
        return;
      }
      row.currentData[key] = row.validator.controls[item].value;
    });
    this.usersService.modifyUser(row.currentData).subscribe({
        next: (u) => {
          row.originalData = u;
          row.editable = false;
          this.toastService.showSuccess("pomyślnie edytowano");
        },
        error: (err: HttpErrorResponse) => {
          let errorMessage: string = err.error.message;
          this.toastService.showDanger(errorMessage);
        }
      }
    );
  }

  formatRoles(roles: Role[] | undefined) {
      if(!roles) {
        return
      }
      return roles.map<string>(r => `${r.name}`).join(', ');
  }

  cancel(row: EditUser) {
    row.editable = false;
    Object.keys(row.validator.controls).forEach(item => {
      if (item === 'roles') {
        return;
      }
      let test: keyof UserShortInfo = item as any;
      if (row.currentData) {
        row.validator.controls[item].patchValue(row.currentData[test]);
      }
    });
    row.validator.controls['roles'].patchValue(row.currentData?.roles.map<any>(x => x.roleId))
  }

  delete(row: EditUser) {

  }
}
