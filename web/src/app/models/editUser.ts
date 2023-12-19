import {User} from "./user";
import {FormGroup} from "@angular/forms";
import {Role} from "./role";

export interface EditUser {
  currentData?: UserShortInfo;
  originalData: UserShortInfo;
  editable: boolean;
  validator: FormGroup;
}

export interface UserShortInfo {
  userId?: string;
  login: string;
  name: string;
  surname: string;
  email: string;
  roles: Role[];
}
