import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConsumerRoutingModule } from './consumer-routing.module';
import {SharedModule} from '../../shared/shared.module';
import {ConsumerDetailComponent} from './consumer-detail/consumer-detail.component';
import {ConsumerHomeComponent} from './consumer-dashboard/consumer-dashboard.component';
import {ConsumerTableComponent} from './consumer-table/consumer-table.component';

@NgModule({
  declarations: [
    ConsumerDetailComponent,
    ConsumerTableComponent,
    ConsumerHomeComponent
  ],
  imports: [
    CommonModule,
    ConsumerRoutingModule,
    SharedModule
  ],
  exports: [ConsumerHomeComponent, ConsumerDetailComponent]
})
export class ConsumerModule { }
