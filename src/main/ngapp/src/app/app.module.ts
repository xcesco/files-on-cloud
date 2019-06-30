import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './layout/header/header.component';
import {FooterComponent} from './layout/footer/footer.component';
import {HomeComponent} from './home/home.component';
import {AdminModule} from './features/admin/admin.module';
import {SharedModule} from './shared/shared.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UploaderModule} from './features/uploader/uploader.module';
import {ConsumerModule} from './features/consumer/consumer.module';
import {CloudFileModule} from './features/cloud-file/cloud-file.module';
import {LoginComponent} from './login/login.component';
import {environment} from '../environments/environment';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFireModule} from '@angular/fire';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TokenInterceptor} from './interceptors/token.interceptor';
import { SignupComponent } from './signup/signup.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // required animations module
    SharedModule,

    AdminModule,
    UploaderModule,
    ConsumerModule,
    CloudFileModule,

    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,

    AppRoutingModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    // {provide: LOCALE_ID, useValue: 'it-IT'}
  ]
  ,
  bootstrap: [AppComponent]
})
export class AppModule {
}
