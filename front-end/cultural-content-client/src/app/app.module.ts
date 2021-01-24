import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatNativeDateModule } from '@angular/material/core';
import { MaterialModule } from './material-module';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './core/navigation-bar/navigation-bar.component';
import { MapPageComponent } from './modules/cultural-offer/map-page/map-page.component';
import { RegistrationComponent } from './modules/registration/registration.component';
import { MapItemComponent } from './modules/cultural-offer/map-item/map-item.component';
import { CulturalOfferService } from './services/cultural-offer/cultural-offer.service';
import { MatGridListModule } from '@angular/material/grid-list';
import { CulturalOfferTypeService } from './services/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from './services/cultural-offer-subtype/cultural-offer-sub-type.service';
import { MapFilterFormComponent } from './modules/cultural-offer/map-filter-form/map-filter-form.component';
import { MatCardModule } from '@angular/material/card';
import { NewsComponent } from './modules/news/news-component/news.component';
import { PaginationBarComponent } from './core/pagination-bar/pagination-bar.component';
import { HttpErrorInterceptor } from './interceptors/http-error.interceptor';
import { CulturalOfferTypePageComponent } from './modules/cultural-offer-type/cultural-offer-type-page/cultural-offer-type-page.component';
import { CulturalOfferTypeListComponent } from './modules/cultural-offer-type/cultural-offer-type-list/cultural-offer-type-list.component';
import { ConfirmDeleteComponent } from './core/confirm-delete/confirm-delete.component';
import { NewsFormComponent } from './modules/news/news-form/news-form.component';
import { MatCarouselModule } from '@ngmodule/material-carousel';
import { PageNotFoundComponent } from './modules/page-not-found/page-not-found.component';
import { LoginComponent } from './modules/login/login.component';
import { SnackBarComponent } from './core/snack-bar/snack-bar.component';
import { SubtypeListComponent } from './modules/cultural-offer-subtype/subtype-list/subtype-list.component';
import { SubtypeFormComponent } from './modules/cultural-offer-subtype/subtype-form/subtype-form.component';
import { NewCulturalOfferDialogComponent } from './modules/cultural-offer/new-cultural-offer-dialog/new-cultural-offer-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { NgxMatFileInputModule } from '@angular-material-components/file-input';
import { MapItemOverviewComponent } from './modules/cultural-offer/map-item-overview/map-item-overview.component';
import { DynamicComponentService } from './services/dynamic-component.service';
import { StarRatingComponent } from './modules/star-rating/star-rating.component';
import { TypeFormComponent } from './modules/cultural-offer-type/type-form/type-form.component';
import { ResendActivationComponent } from './modules/resend-activation/resend-activation.component';
import { TokenInterceptor } from './interceptors/token.interceptor';

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
    ConfirmDeleteComponent,
    PageNotFoundComponent,
    LoginComponent,
    NewsFormComponent,
    SnackBarComponent,
    SubtypeListComponent,
    SubtypeFormComponent,
    NewCulturalOfferDialogComponent,
    MapItemOverviewComponent,
    StarRatingComponent,
    TypeFormComponent,
    ResendActivationComponent,
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
    ReactiveFormsModule,
    MatCarouselModule.forRoot(),
    MatDialogModule,
    NgxMatFileInputModule,
  ],
  providers: [
    CulturalOfferService,
    CulturalOfferTypeService,
    CulturalOfferSubTypeService,
    SnackBarComponent,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    DynamicComponentService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
