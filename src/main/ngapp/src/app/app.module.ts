import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AdminsTableComponent} from './administrators/admins-table/admins-table.component';
import {HeaderComponent} from './layout/header/header.component';
import {FooterComponent} from './layout/footer/footer.component';
import {HttpClientModule} from '@angular/common/http';
import { HomeComponent } from './home/home/home.component';
import { AdminsDetailComponent } from './administrators/admins-detail/admins-detail.component';
import { AdminsHomeComponent } from './administrators/admins-home.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminsTableComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    AdminsDetailComponent,
    AdminsHomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
