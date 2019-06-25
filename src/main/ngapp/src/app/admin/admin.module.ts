import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminHomeComponent} from './admin-home.component';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {AdminPasswordComponent} from './admin-password/admin-password.component';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  declarations: [
    AdminDetailComponent,
    AdminHomeComponent,
    AdminPasswordComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ], exports: [
    AdminHomeComponent
  ]
})
export class AdminModule {
}
