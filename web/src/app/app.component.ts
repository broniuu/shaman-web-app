import {Component, OnInit} from '@angular/core';
import {StubTokenService} from "./services/stub-token.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'shaman-web-app';

  constructor(private stubTokeService: StubTokenService) {}

  ngOnInit(): void {
    // this.stubTokeService.getAndSaveTokenToLocalStorage().subscribe(() => {
    //   let token = localStorage.getItem('token');
    //   console.log(token);
    // });
  }


}
