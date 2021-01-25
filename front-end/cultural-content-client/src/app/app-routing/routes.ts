import {Routes } from '@angular/router';
import { LoginComponent } from '../modules/login/login.component';
import { NewsComponent } from '../modules/news/news-component/news.component';
import { PageNotFoundComponent } from '../modules/page-not-found/page-not-found.component';
import { CulturalOfferTypePageComponent } from '../modules/cultural-offer-type/cultural-offer-type-page/cultural-offer-type-page.component';
import { MapPageComponent } from '../modules/cultural-offer/map-page/map-page.component';
import { RegistrationComponent } from '../modules/registration/registration.component';
import { AdminGuard, LoginGuard } from '../guards';
import { ResendActivationComponent } from '../modules/resend-activation/resend-activation.component';

export const routes: Routes = [
    { path: '', component: MapPageComponent },
    { path: 'culturalOfferTypes', component: CulturalOfferTypePageComponent, canActivate: [AdminGuard] },
    { path: 'news', component: NewsComponent },
    { path: 'register', component: RegistrationComponent, canActivate: [LoginGuard] },
    { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
    { path: 'resend-activation', component: ResendActivationComponent, canActivate: [LoginGuard] },
    { path: '**', component: PageNotFoundComponent },
  ];