export class User {
  constructor(
    public login: string,
    public password: string,
    public name: string,
    public surname: string,
    public address: string,
    public debitCardNumber: string,
    public expireDate: string,
    public cvv: string,
    public email: string) {
  }
}
