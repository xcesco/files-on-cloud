import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AdminDashboardComponent} from './features/admin/admin-dashboard/admin-dashboard.component';
import {UploaderDashboardComponent} from './features/uploader/uploader-dashboard/uploader-dashboard.component';
import {ConsumerHomeComponent} from './features/consumer/consumer-dashboard/consumer-dashboard.component';
import {CloudFileDashboardComponent} from './features/cloud-file/cloud-file-dashboard/cloud-file-dashboard.component';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'admins', component: AdminDashboardComponent,
    loadChildren: () => import('./features/admin/admin.module').then(mod => mod.AdminModule)
  },
  {
    path: 'uploaders', component: UploaderDashboardComponent,
    loadChildren: () => import('./features/uploader/uploader.module').then(mod => mod.UploaderModule)
  },
  {
    path: 'consumers', component: ConsumerHomeComponent,
    loadChildren: () => import('./features/consumer/consumer.module').then(mod => mod.ConsumerModule)
  },
  {
    path: 'files', component: CloudFileDashboardComponent,
    loadChildren: () => import('./features/cloud-file/cloud-file.module').then(mod => mod.CloudFileModule)
  },
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
