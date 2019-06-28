import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CloudFileRoutingModule} from './cloud-file-routing.module';
import {CloudFileDashboardComponent} from './cloud-file-dashboard/cloud-file-dashboard.component';
import {CloudFileTableComponent} from './cloud-file-table/cloud-file-table.component';
import {CloudFileDetailComponent} from './cloud-file-detail/cloud-file-detail.component';
import {SharedModule} from '../../shared/shared.module';

@NgModule({
  declarations: [
    CloudFileDashboardComponent,
    CloudFileTableComponent,
    CloudFileDetailComponent],
  imports: [
    CommonModule,
    CloudFileRoutingModule,
    SharedModule
  ],
  exports: [CloudFileDashboardComponent]
})
export class CloudFileModule {
}
