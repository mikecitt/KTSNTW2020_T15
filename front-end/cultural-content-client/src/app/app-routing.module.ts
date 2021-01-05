import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { MapPageComponent} from './pages/map-page/map-page.component'
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  { path: '', component: NavigationBarComponent, children: [ {path: '', component:MapPageComponent} ] },
  { path: 'register', component: RegistrationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
