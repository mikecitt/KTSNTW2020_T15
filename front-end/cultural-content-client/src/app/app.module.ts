import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MatNativeDateModule } from '@angular/material/core';
import { MaterialModule } from './material-module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';

@NgModule({
  declarations: [AppComponent, NavigationBarComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    MatNativeDateModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
