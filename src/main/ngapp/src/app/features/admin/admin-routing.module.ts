import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminTableComponent} from './admin-table/admin-table.component';
import {AdminListResolver} from '../../resolvers/admin-list-resolver.service';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {ROUTE_PARAM_USER_ID} from '../../app-routing.costant';
import {AdminReadResolver} from '../../resolvers/admin-read-resolver.service';
import {AdminCreateResolver} from '../../resolvers/admin-create-resolver.service';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  {
    path: 'admins', component: AdminDashboardComponent, children: [
      {path: '', component: AdminTableComponent, resolve: {list: AdminListResolver}},
      {
        path: 'create', component: AdminDetailComponent, resolve: {detail: AdminCreateResolver}
      },
      {
        path: ':' + ROUTE_PARAM_USER_ID, component: AdminDetailComponent, resolve: {detail: AdminReadResolver}
      }]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
