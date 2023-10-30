import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RestaurantsComponent} from "./components/restaurants/restaurants.component";
import {AppComponent} from "./app.component";
import {RegistrationComponent} from "./components/registration/registration.component";

const routes: Routes = [{ path: '', component: AppComponent },
  { path: 'Restaurants', component: RestaurantsComponent },
  {
    path: 'register',
    component: RegistrationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
