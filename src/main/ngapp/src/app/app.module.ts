import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModal, NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AdminTableComponent} from './admin/admin-table/admin-table.component';
import {HeaderComponent} from './layout/header/header.component';
import {FooterComponent} from './layout/footer/footer.component';
import {HttpClientModule} from '@angular/common/http';
import {HomeComponent} from './home/home/home.component';
import {AdminDetailComponent} from './admin/admin-detail/admin-detail.component';
import {AdminHomeComponent} from './admin/admin-home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AdminPasswordComponent } from './admin/admin-password/admin-password.component';
import { UploaderHomeComponent } from './uploader/uploader-home.component';
import { UploaderDetailComponent } from './uploader/uploader-detail/uploader-detail.component';
import { UploaderTableComponent } from './uploader/uploader-table/uploader-table.component';
import { UploaderPasswordComponent } from './uploader/uploader-password/uploader-password.component';
import { ConsumerHomeComponent } from './consumer/consumer-home.component';
import { ConsumerPasswordComponent } from './consumer/consumer-password/consumer-password.component';
import { ConsumerTableComponent } from './consumer/consumer-table/consumer-table.component';
import { ConsumerDetailComponent } from './consumer/consumer-detail/consumer-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminTableComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    AdminDetailComponent,
    AdminHomeComponent,
    AdminPasswordComponent,
    UploaderHomeComponent,
    UploaderDetailComponent,
    UploaderTableComponent,
    UploaderPasswordComponent,
    ConsumerHomeComponent,
    ConsumerPasswordComponent,
    ConsumerTableComponent,
    ConsumerDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,

    FormsModule,
    ReactiveFormsModule,

    NgbModule,
    NgbModalModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
