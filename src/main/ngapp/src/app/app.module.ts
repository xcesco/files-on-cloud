import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

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
import {NgbAuthFirebaseUIModule} from '@firebaseui/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // required animations module
    SharedModule,

    AdminModule,
    UploaderModule,
    ConsumerModule,
    CloudFileModule,

    AppRoutingModule,

    NgbAuthFirebaseUIModule.forRoot(environment.firebase)

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
