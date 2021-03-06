import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminTableComponent} from './admin-table/admin-table.component';
import {AdminListResolver} from '../../resolvers/admin-list-resolver.service';
import {AdminDetailComponent} from './admin-detail/admin-detail.component';
import {ROUTE_PARAM_USER_ID} from '../../app-routing.costant';
import {AdminReadResolver} from '../../resolvers/admin-read-resolver.service';
import {AdminCreateResolver} from '../../resolvers/admin-create-resolver.service';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {AdminReportComponent} from './admin-report/admin-report.component';
import {AdminReportResolver} from '../../resolvers/admin-summary-resolver.service';
import {AuthAdministratorGuard} from '../../shared/guards/auth-administrator-guard.service';

const routes: Routes = [
  {
    path: 'administrators', component: AdminDashboardComponent, canActivate: [AuthAdministratorGuard], canActivateChild: [AuthAdministratorGuard], children: [
      {path: '', pathMatch: 'full', redirectTo: 'list'},
      {path: 'list', component: AdminTableComponent, resolve: {list: AdminListResolver}},
      {
        path: 'create', component: AdminDetailComponent, resolve: {detail: AdminCreateResolver}
      },
      {
        path: 'report', component: AdminReportComponent, resolve: {list: AdminReportResolver}
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
