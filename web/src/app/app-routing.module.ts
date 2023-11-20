import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RestaurantsComponent} from "./components/restaurants/restaurants.component";
import {AppComponent} from "./app.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {DishComponent} from "./components/dish/dish.component";
import {DishesComponent} from "./components/dishes/dishes.component";
import {LoginComponent} from "./components/login/login.component";
import {SettingsComponent} from "./components/settings/settings.component";

const routes: Routes = [{ path: '', component: AppComponent },
  { path: 'Restaurants', component: RestaurantsComponent },
  { path: 'Restaurants/:restaurantName/:p', component: DishesComponent },
  { path: 'Restaurant/:restaurantName/:dishName', component: DishComponent },
  {
    path: 'register',
    component: RegistrationComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
