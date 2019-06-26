import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AdminHomeComponent} from './features/admin/admin-home.component';
import {UploaderHomeComponent} from './features/uploader/uploader-home.component';
import {ConsumerHomeComponent} from './features/consumer/consumer-home.component';

const routes: Routes = [
  {
    path: 'admins', component: AdminHomeComponent,
    loadChildren: () => import('./features/admin/admin.module').then(mod => mod.AdminModule)
  },
  {
    path: 'uploaders', component: UploaderHomeComponent,
    loadChildren: () => import('./features/uploader/uploader.module').then(mod => mod.UploaderModule)
  },
  {
    path: 'consumers', component: ConsumerHomeComponent,
    loadChildren: () => import('./features/consumer/consumer.module').then(mod => mod.ConsumerModule)
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
