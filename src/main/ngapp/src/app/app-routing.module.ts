import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminListResolver} from './resolvers/admin-resolver.service';
import {HomeComponent} from './home/home/home.component';
import {AdminsHomeComponent} from './administrators/admins-home.component';
import {AdminsTableComponent} from './administrators/admins-table/admins-table.component';
import {AdminsDetailComponent} from './administrators/admins-detail/admins-detail.component';
import {AdminCreateResolver} from './resolvers/admin-create-resolver.service';
import {AdminReadResolver} from './resolvers/admin-read-resolver.service';

const routes: Routes = [
  {
    path: 'administrators', component: AdminsHomeComponent,
    children: [
      {
        path: 'list', component: AdminsTableComponent, resolve: {list: AdminListResolver}
      },
      {
        path: 'create', component: AdminsDetailComponent, resolve: {detail: AdminCreateResolver}
      },
      {
        path: 'read/:{adminId}', component: AdminsDetailComponent, resolve: {detail: AdminReadResolver}
      }

    ]

  },
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
