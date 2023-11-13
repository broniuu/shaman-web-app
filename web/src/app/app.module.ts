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
import { RestaurantsComponent } from './components/restaurants/restaurants.component';
import { DishesComponent } from './components/dishes/dishes.component';
import { DishComponent } from './components/dish/dish.component';
import { RestaurantItemComponent } from './components/restaurants/restaurant-item/restaurant-item.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import {FormsModule} from "@angular/forms";
import { DishItemComponent } from './components/dishes/dish-item/dish-item.component';
import {NgOptimizedImage} from "@angular/common";
import { FilterTextPipe } from './pipes/filter-text.pipe';

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
    DishItemComponent,
    FilterTextPipe
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
  providers: [StubTokenService],
  bootstrap: [AppComponent],
})
export class AppModule {}
