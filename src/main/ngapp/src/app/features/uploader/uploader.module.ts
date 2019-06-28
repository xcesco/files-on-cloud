import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UploaderRoutingModule} from './uploader-routing.module';
import {SharedModule} from '../../shared/shared.module';
import {UploaderDetailComponent} from './uploader-detail/uploader-detail.component';
import {UploaderDashboardComponent} from './uploader-dashboard/uploader-dashboard.component';
import {UploaderTableComponent} from './uploader-table/uploader-table.component';

@NgModule({
  declarations: [
    UploaderDetailComponent,
    UploaderTableComponent,
    UploaderDashboardComponent
  ],
  imports: [
    CommonModule,
    UploaderRoutingModule,
    SharedModule
  ],
  exports: [UploaderDashboardComponent]
})
export class UploaderModule {
}
