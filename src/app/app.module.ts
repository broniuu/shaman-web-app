import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {StubTokenService} from "./services/stub-token.service";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RestaurantsComponent } from './restaurants/restaurants.component';
import { DishesComponent } from './dishes/dishes.component';
import { DishComponent } from './dish/dish.component';

// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    RestaurantsComponent,
    DishesComponent,
    DishComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [StubTokenService],
  bootstrap: [AppComponent]
})
export class AppModule {}
