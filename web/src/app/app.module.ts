import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {StubTokenService} from "./services/stub-token.service";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RegistrationComponent } from './components/registration/registration.component';
import {CreditCardDirectivesModule} from "angular-cc-library";
import {ReactiveFormsModule} from "@angular/forms";
import { TextInputComponent } from './components/text-input/text-input.component';
import { RestaurantsComponent } from './components/restaurants/restaurants.component';
import { DishesComponent } from './components/dishes/dishes.component';
import { DishComponent } from './components/dish/dish.component';
import { RestaurantItemComponent } from './components/restaurants/restaurant-item/restaurant-item.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import {NgOptimizedImage} from "@angular/common";
import { SettingsComponent } from './components/settings/settings.component';
import { ToastsContainerComponent } from './components/toasts-container/toasts-container.component';
import { JwtInterceptor } from './interceptors/jwt/jwt.interceptor';

// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    TextInputComponent,
    RestaurantsComponent,
    DishesComponent,
    DishComponent,
    RestaurantItemComponent,
    SearchBarComponent,
    LoginComponent,
    SettingsComponent,
    ToastsContainerComponent
  ],
  imports: [

    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    CreditCardDirectivesModule,
    ReactiveFormsModule,
    FormsModule,
    NgOptimizedImage
  ],
  providers: [
    StubTokenService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
