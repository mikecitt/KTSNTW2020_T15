import { SubsriptionService } from './service/subscription/subsription.service';
import { NewsService } from './service/news/news.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { MatNativeDateModule } from '@angular/material/core';
import { MaterialModule } from './material-module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { MapPageComponent } from './pages/map-page/map-page.component';
import { MapItemComponent } from './map-item/map-item.component';
import {CulturalOfferService } from './service/cultural-offer/cultural-offer.service'
import { MatGridListModule } from '@angular/material/grid-list';
import { CulturalOfferTypeService } from './service/cultural-offer-type/cultural-offer-type.service';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CulturalOfferSubTypeService } from './service/cultural-offer-subtype/cultural-offer-sub-type.service';
import { MapFilterFormComponent } from './map-filter-form/map-filter-form.component';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule } from '@angular/forms';
import { NewsComponent } from './news/news.component';
import { PaginationBarComponent } from './pagination-bar/pagination-bar.component';
import { HttpErrorInterceptor } from './helpers/http-error.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    MapPageComponent,
    MapItemComponent,
    MapFilterFormComponent,
    NewsComponent,
    PaginationBarComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    MatNativeDateModule,
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
