import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {StubTokenService} from "./services/stub-token.service";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RegistrationComponent } from './components/registration/registration.component';
import {CreditCardDirectivesModule} from "angular-cc-library";
import {ReactiveFormsModule} from "@angular/forms";
import { TextInputComponent } from './components/text-input/text-input.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    TextInputComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    CreditCardDirectivesModule,
    ReactiveFormsModule
  ],
  providers: [StubTokenService],
  bootstrap: [AppComponent],
})
export class AppModule { }
