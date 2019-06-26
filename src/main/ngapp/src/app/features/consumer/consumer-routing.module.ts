import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ROUTE_PARAM_USER_ID} from '../../app-routing.costant';
import {ConsumerDetailComponent} from './consumer-detail/consumer-detail.component';
import {ConsumerListResolver} from '../../resolvers/consumer-list-resolver.service';
import {ConsumerTableComponent} from './consumer-table/consumer-table.component';
import {ConsumerReadResolver} from '../../resolvers/consumer-read-resolver.service';
import {ConsumerCreateResolver} from '../../resolvers/consumer-create-resolver.service';

const routes: Routes = [
  {
    path: 'list', component: ConsumerTableComponent, resolve: {list: ConsumerListResolver}
  },
  {
    path: 'create', component: ConsumerDetailComponent, resolve: {detail: ConsumerCreateResolver}
  },
  {
    path: ':' + ROUTE_PARAM_USER_ID, component: ConsumerDetailComponent, resolve: {detail: ConsumerReadResolver}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConsumerRoutingModule { }
