import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ROUTE_PARAM_USER_ID} from '../../app-routing.costant';
import {UploaderDetailComponent} from './uploader-detail/uploader-detail.component';
import {UploaderTableComponent} from './uploader-table/uploader-table.component';
import {UploaderListResolver} from '../../resolvers/uploader-list-resolver.service';
import {UploaderCreateResolver} from '../../resolvers/uploader-create-resolver.service';
import {UploaderReadResolver} from '../../resolvers/uploader-read-resolver.service';
import {UploaderDashboardComponent} from './uploader-dashboard/uploader-dashboard.component';

const routes: Routes = [
  {
    path: 'uploaders', component: UploaderDashboardComponent, children: [
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
