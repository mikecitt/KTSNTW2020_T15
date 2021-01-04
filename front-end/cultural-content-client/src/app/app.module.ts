import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

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

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    MapPageComponent,
    MapItemComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    MatNativeDateModule,
    HttpClientModule,
    MatGridListModule,
    NoopAnimationsModule,
  ],
  providers: [
    CulturalOfferService,
    CulturalOfferTypeService,
    CulturalOfferSubTypeService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
