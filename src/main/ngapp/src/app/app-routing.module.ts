import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminListResolver} from './resolvers/admin-resolver.service';
import {HomeComponent} from './home/home/home.component';
import {AdminHomeComponent} from './admin/admin-home.component';
import {AdminTableComponent} from './admin/admin-table/admin-table.component';
import {AdminDetailComponent} from './admin/admin-detail/admin-detail.component';
import {AdminCreateResolver} from './resolvers/admin-new-resolver.service';
import {AdminReadResolver} from './resolvers/admin-get-resolver.service';
import {ROUTE_PARAM_USER_ID} from './app-routing.costant';
import {AdminPasswordComponent} from './admin/admin-password/admin-password.component';
import {AdminPasswordUrlResolver} from './resolvers/admin-password-url-resolver.service';
import {ConsumerHomeComponent} from './consumer/consumer-home.component';
import {UploaderHomeComponent} from './uploader/uploader-home.component';
import {UploaderTableComponent} from './uploader/uploader-table/uploader-table.component';
import {UploaderDetailComponent} from './uploader/uploader-detail/uploader-detail.component';
import {UploaderPasswordComponent} from './uploader/uploader-password/uploader-password.component';
import {ConsumerTableComponent} from './consumer/consumer-table/consumer-table.component';
import {ConsumerDetailComponent} from './consumer/consumer-detail/consumer-detail.component';
import {ConsumerPasswordComponent} from './consumer/consumer-password/consumer-password.component';

const routes: Routes = [
  {
    path: 'admins', component: AdminHomeComponent,
    children: [
      {
        path: 'list', component: AdminTableComponent, resolve: {list: AdminListResolver}
      },
      {
        path: 'create', component: AdminDetailComponent, resolve: {detail: AdminCreateResolver}
      },
      {
        path: ':' + ROUTE_PARAM_USER_ID + '/change-password',
        component: AdminPasswordComponent,
        resolve: {passwordUrl: AdminPasswordUrlResolver}
      },
      {
        path: ':' + ROUTE_PARAM_USER_ID, component: AdminDetailComponent, resolve: {detail: AdminReadResolver}
      }

    ]

  }
  // ,
  // {
  //   path: 'uploaders', component: UploaderHomeComponent,
  //   children: [
  //     {
  //       path: 'list', component: UploaderTableComponent, resolve: {list: UploaderListResolver}
  //     },
  //     {
  //       path: 'create', component: UploaderDetailComponent, resolve: {detail: UploaderCreateResolver}
  //     },
  //     {
  //       path: ':' + ROUTE_PARAM_USER_ID + '/change-password',
  //       component: UploaderPasswordComponent,
  //       resolve: {passwordUrl: UploaderPasswordUrlResolver}
  //     },
  //     {
  //       path: ':' + ROUTE_PARAM_USER_ID, component: UploaderDetailComponent, resolve: {detail: UploaderReadResolver}
  //     }
  //
  //   ]
  //
  // },
  // {
  //   path: 'consumers', component: ConsumerHomeComponent,
  //   children: [
  //     {
  //       path: 'list', component: ConsumerTableComponent, resolve: {list: ConsumerListResolver}
  //     },
  //     {
  //       path: 'create', component: ConsumerDetailComponent, resolve: {detail: ConsumerCreateResolver}
  //     },
  //     {
  //       path: ':' + ROUTE_PARAM_USER_ID + '/change-password',
  //       component: ConsumerPasswordComponent,
  //       resolve: {passwordUrl: ConsumerPasswordUrlResolver}
  //     },
  //     {
  //       path: ':' + ROUTE_PARAM_USER_ID, component: ConsumerDetailComponent, resolve: {detail: ConsumerReadResolver}
  //     }
  //
  //   ]
  //
  // }
  ,
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: '**', component: HomeComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
