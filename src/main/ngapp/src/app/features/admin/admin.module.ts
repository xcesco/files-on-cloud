import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {SharedModule} from '../../shared/shared.module';
import {AdminTableComponent} from './admin-table/admin-table.component';
import {AdminRoutingModule} from './admin-routing.module';

@NgModule({
  declarations: [
    AdminDetailComponent,
    AdminTableComponent,
    AdminDashboardComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ], exports: [
    AdminDashboardComponent
  ]
})
export class AdminModule {
}
