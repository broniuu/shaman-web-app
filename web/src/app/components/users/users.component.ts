import {Component, NgModule, OnInit} from '@angular/core';
import {UsersService} from "../../services/users/users.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {EditUser, UserShortInfo} from "../../models/editUser";
import {ToastService} from "../../services/toast/toast.service";
import {HttpErrorResponse} from "@angular/common/http";
import {MatTableDataSource} from "@angular/material/table";
import {Role} from "../../models/role";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit{

  editUsers: EditUser[] = [];
  displayedColumns: string[] = ['name', 'surname', 'login', 'email', 'roles', 'action'];
  dataSource: MatTableDataSource<EditUser>;
  rolesData: Role[];
  constructor(private usersService: UsersService, private toastService: ToastService) {
  }
  ngOnInit(): void {
    const editForm = (e: UserShortInfo) => new FormGroup({
      name: new FormControl(e.name,Validators.required),
      email: new FormControl(e.email, [Validators.required, Validators.email]),
      login: new FormControl(e.login,Validators.required),
      surname: new FormControl(e.surname,Validators.required),
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
      next: (roles) => this.rolesData = roles,
      error: (err: HttpErrorResponse) => {
      let apiError = err.error;
      let errorMessage = apiError.message;
      this.toastService.showDanger(errorMessage);
      console.log(err);
    }
  })
  }

  assertItemType(item: EditUser): EditUser {
    return item
  }

  public compareRoles(role1: Role, role2: Role) {
    return role1 && role2 ? role1.roleId === role2.roleId : false;
  }

  startEdit(row: EditUser) {
    row.editable = true;
  }

  confirmEditCreate(row: any) {

  }

  cancel(row: EditUser) {
    row.editable = false;
    Object.keys(row.validator.controls).forEach(item => {
      if (item === 'roles') {return;}
      let test: keyof UserShortInfo = item as any;
      if(row.currentData){
        row.validator.controls[item].patchValue(row.currentData[test]);
      }
    });
    row.validator.controls['roles'].patchValue(row.currentData?.roles.map<any>(x => x.roleId))
  }

  delete(row: EditUser) {

  }

  protected readonly FormBuilder = FormBuilder;
  protected readonly FormControl = FormControl;
}
