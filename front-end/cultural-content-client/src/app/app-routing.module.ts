import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { MapPageComponent} from './pages/map-page/map-page.component'
import { RegistrationComponent } from './registration/registration.component';
import { CulturalOfferTypePageComponent } from './pages/cultural-offer-type-page/cultural-offer-type-page.component';
import { NewsComponent } from './news/news.component'

const routes: Routes = [
  { path: '', component: MapPageComponent},
  { path: 'culturalOfferTypes', component: CulturalOfferTypePageComponent},
  { path: 'news', component: NewsComponent},
  { path: 'register', component: RegistrationComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
