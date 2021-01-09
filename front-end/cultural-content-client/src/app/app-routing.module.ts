import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapPageComponent} from './pages/map-page/map-page.component'
import { CulturalOfferTypePageComponent } from './pages/cultural-offer-type-page/cultural-offer-type-page.component';

const routes: Routes = [
  { path: '', component: MapPageComponent},
  { path: 'culturalOfferTypes', component: CulturalOfferTypePageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
