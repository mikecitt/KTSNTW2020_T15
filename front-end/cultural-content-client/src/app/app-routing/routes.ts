import {Routes } from '@angular/router';
import { LoginComponent } from '../modules/login/login.component';
import { NewsComponent } from '../modules/news/news.component';
import { PageNotFoundComponent } from '../modules/page-not-found/page-not-found.component';
import { CulturalOfferTypePageComponent } from '../modules/cultural-offer-type/cultural-offer-type-page/cultural-offer-type-page.component';
import { MapPageComponent } from '../modules/cultural-offer/map-page/map-page.component';
import { RegistrationComponent } from '../modules/registration/registration.component';

export const routes: Routes = [
    { path: '', component: MapPageComponent },
    { path: 'culturalOfferTypes', component: CulturalOfferTypePageComponent },
    { path: 'news', component: NewsComponent },
    { path: 'register', component: RegistrationComponent },
    { path: 'login', component: LoginComponent },
    { path: '**', component: PageNotFoundComponent },
  ];