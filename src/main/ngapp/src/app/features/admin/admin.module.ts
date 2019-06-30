import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {SharedModule} from '../../shared/shared.module';
import {AdminTableComponent} from './admin-table/admin-table.component';
import {AdminRoutingModule} from './admin-routing.module';
import { AdminReportComponent } from './admin-report/admin-report.component';

@NgModule({
  declarations: [
    AdminDetailComponent,
    AdminTableComponent,
    AdminDashboardComponent,
    AdminReportComponent
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
