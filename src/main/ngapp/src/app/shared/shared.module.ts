import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManagedErrorDirective} from './directives/managed-error.directive';
import {UserDetailComponent} from './components/user-detail/user-detail.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from '../app-routing.module';
import {DisplayManagedErrorComponent} from './components/display-managed-error/display-managed-error.component';
import {ConfirmationDialogComponent} from './components/confirmation-dialog/confirmation-dialog.component';
import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationDialogService} from './components/confirmation-dialog/confirmation-dialog.service';


@NgModule({
  declarations: [
    ManagedErrorDirective,
    UserDetailComponent,
    DisplayManagedErrorComponent,
    ConfirmationDialogComponent
  ],
  providers: [
    ConfirmationDialogService
  ],
  imports: [
    CommonModule,
    AppRoutingModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

    NgbModule,
    NgbModalModule
  ],
  exports: [
    CommonModule,
    AppRoutingModule,

    FormsModule,
    ReactiveFormsModule,

    ManagedErrorDirective,
    UserDetailComponent,
    DisplayManagedErrorComponent,

    NgbModule,
    NgbModalModule
  ],
  entryComponents: [ConfirmationDialogComponent]
})
export class SharedModule {
}
