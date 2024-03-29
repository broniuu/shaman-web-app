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
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import {CommonModule, NgOptimizedImage} from "@angular/common";

import { DishItemComponent } from './components/dishes/dish-item/dish-item.component';
import { FilterTextPipe } from './pipes/filterTextPipe/filter-text.pipe';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatExpansionModule} from "@angular/material/expansion";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatNativeDateModule, MatOptionModule} from "@angular/material/core"
import {MatMenuModule} from "@angular/material/menu";
import {MatSelectModule} from "@angular/material/select";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { SettingsComponent } from './components/settings/settings.component';
import {ToastsContainerComponent } from './components/toasts-container/toasts-container.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { JwtInterceptor } from './interceptors/jwt/jwt.interceptor';
import { UsersComponent } from './components/users/users.component';
import {MatTableModule} from "@angular/material/table";
import { TypeSafeMatCellDef } from './directives/type-safe-mat-cell-def.directive';
import { FormControlPipe } from './directives/formControll/form-controll.pipe';
import {EditPasswordComponent} from "./components/edit-password/edit-password.component";
import {MatSortModule} from "@angular/material/sort";
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
    DishItemComponent,
    FilterTextPipe,
    NavbarComponent,
    LoginComponent,
    LoginComponent,
    SettingsComponent,
    ToastsContainerComponent,
    UserFormComponent,
    ShoppingCartComponent,
    UsersComponent,
    TypeSafeMatCellDef,
    FormControlPipe,
    EditPasswordComponent,
    EditPasswordComponent
  ],
  providers: [
    StubTokenService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  imports: [
    MatInputModule,
    MatMenuModule,
    MatExpansionModule,
    MatOptionModule,
    MatSelectModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatButtonModule,
    MatExpansionModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    CreditCardDirectivesModule,
    ReactiveFormsModule,
    FormsModule,
    NgOptimizedImage,
    BrowserAnimationsModule,
    MatTableModule,
    MatSortModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
