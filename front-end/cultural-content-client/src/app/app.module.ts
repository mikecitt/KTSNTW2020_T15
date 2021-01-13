import { SubsriptionService } from './service/subscription/subsription.service';
import { NewsService } from './service/news/news.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatNativeDateModule } from '@angular/material/core';
import { MaterialModule } from './material-module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { MapPageComponent } from './pages/map-page/map-page.component';
import { RegistrationComponent } from './registration/registration.component';
import { MapItemComponent } from './map-item/map-item.component';
import { CulturalOfferService } from './service/cultural-offer/cultural-offer.service'
import { MatGridListModule } from '@angular/material/grid-list';
import { CulturalOfferTypeService } from './service/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from './service/cultural-offer-subtype/cultural-offer-sub-type.service';
import { MapFilterFormComponent } from './map-filter-form/map-filter-form.component';
import { MatCardModule } from '@angular/material/card';
import { NewsComponent } from './news/news.component';
import { PaginationBarComponent } from './pagination-bar/pagination-bar.component';
import { HttpErrorInterceptor } from './helpers/http-error.interceptor';
import { CulturalOfferTypePageComponent } from './pages/cultural-offer-type-page/cultural-offer-type-page.component';
import { CulturalOfferTypeListComponent } from './cultural-offer-type-list/cultural-offer-type-list.component';
import { CreateTypeFormComponent } from './create-type-form/create-type-form.component';
import { UpdateTypeFormComponent } from './update-type-form/update-type-form.component';
import { ConfirmDeleteComponent } from './confirm-delete/confirm-delete.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    MapPageComponent,
    RegistrationComponent,
    MapItemComponent,
    MapFilterFormComponent,
    NewsComponent,
    PaginationBarComponent,
    CulturalOfferTypePageComponent,
    CulturalOfferTypeListComponent,
    CreateTypeFormComponent,
    UpdateTypeFormComponent,
    ConfirmDeleteComponent,
    PageNotFoundComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MaterialModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    NoopAnimationsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatGridListModule,
    NoopAnimationsModule,
    MatCardModule,
    ReactiveFormsModule
  ],
  providers: [
    CulturalOfferService,
    CulturalOfferTypeService,
    CulturalOfferSubTypeService,
    NewsService,
    SubsriptionService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
