import {Role} from "./role";

export interface User {
    login: string;
    password: string;
    name: string;
    surname: string;
    address: string;
    debitCardNumber: string;
    expireDate: string;
    cvv: string;
    email: string;
}
