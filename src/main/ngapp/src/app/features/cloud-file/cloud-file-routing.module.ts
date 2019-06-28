import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ROUTE_PARAM_FILE_UUID} from '../../app-routing.costant';
import {CloudFileDashboardComponent} from './cloud-file-dashboard/cloud-file-dashboard.component';
import {CloudFileTableComponent} from './cloud-file-table/cloud-file-table.component';
import {CloudFileDetailComponent} from './cloud-file-detail/cloud-file-detail.component';
import {CloudFileListResolver} from '../../resolvers/cloud-file-list-resolver.service';
import {CloudFileReadResolver} from '../../resolvers/cloud-file-read-resolver.service';
import {CloudFileCreateResolver} from '../../resolvers/cloud-file-create-resolver.service';

const routes: Routes = [
  {
    path: 'files', component: CloudFileDashboardComponent, children: [
      {
        path: '', component: CloudFileTableComponent, resolve: {list: CloudFileListResolver}
      },
      {
        path: 'create', component: CloudFileDetailComponent, resolve: {detail: CloudFileCreateResolver}
      },
      {
        path: ':' + ROUTE_PARAM_FILE_UUID, component: CloudFileDetailComponent, resolve: {detail: CloudFileReadResolver}
      }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CloudFileRoutingModule {
}
