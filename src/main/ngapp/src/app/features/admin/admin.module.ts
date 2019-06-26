import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminHomeComponent} from './admin-home.component';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {SharedModule} from '../../shared/shared.module';
import {AdminTableComponent} from './admin-table/admin-table.component';
import {AdminRoutingModule} from './admin-routing.module';

@NgModule({
  declarations: [
    AdminDetailComponent,
    AdminTableComponent,
    AdminHomeComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ], exports: [
    AdminHomeComponent
  ]
})
export class AdminModule {
}
