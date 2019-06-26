import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ROUTE_PARAM_USER_ID} from '../../app-routing.costant';
import {UploaderDetailComponent} from './uploader-detail/uploader-detail.component';
import {UploaderTableComponent} from './uploader-table/uploader-table.component';
import {UploaderListResolver} from '../../resolvers/uploader-list-resolver.service';
import {UploaderCreateResolver} from '../../resolvers/uploader-create-resolver.service';
import {UploaderReadResolver} from '../../resolvers/uploader-read-resolver.service';
import {ConsumerListResolver} from '../../resolvers/consumer-list-resolver.service';
import {ConsumerHomeComponent} from '../consumer/consumer-home.component';
import {ConsumerCreateResolver} from '../../resolvers/consumer-create-resolver.service';
import {ConsumerReadResolver} from '../../resolvers/consumer-read-resolver.service';
import {ConsumerTableComponent} from '../consumer/consumer-table/consumer-table.component';
import {ConsumerDetailComponent} from '../consumer/consumer-detail/consumer-detail.component';
import {UploaderHomeComponent} from './uploader-home.component';

const routes: Routes = [
  {
    path: 'uploaders', component: UploaderHomeComponent, children: [
      {path: '', component: UploaderTableComponent, resolve: {list: UploaderListResolver}},
      {
        path: 'create', component: UploaderDetailComponent, resolve: {detail: UploaderCreateResolver}
      },
      {
        path: ':' + ROUTE_PARAM_USER_ID, component: UploaderDetailComponent, resolve: {detail: UploaderReadResolver}
      }]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UploaderRoutingModule {
}