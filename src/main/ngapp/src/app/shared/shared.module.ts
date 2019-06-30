import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManagedErrorDirective} from './directives/managed-error.directive';
import {UserDetailComponent} from './components/user-detail/user-detail.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DisplayManagedErrorComponent} from './components/display-managed-error/display-managed-error.component';
import {ConfirmationDialogComponent} from './components/confirmation-dialog/confirmation-dialog.component';
import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationDialogService} from './components/confirmation-dialog/confirmation-dialog.service';
import {ToastrModule} from 'ngx-toastr';
import {ChangePasswordDialogComponent} from './components/change-password-dialog/change-password-dialog.component';
import {ChangePasswordDialogService} from './components/change-password-dialog/change-password-dialog.service';
import {FileSizePipe} from './pipe/file-size.pipe';
import {SecureIsAdministratorDirective} from './directives/secure-is-administrator.directive';
import {SecureIsUploaderDirective} from './directives/secure-is-uploader.directive';
import {SecureIsConsumerDirective} from './directives/secure-is-consumer.directive';
import {SecureIsAuthenticatedDirective} from './directives/secure-is-authenticated.directive';


@NgModule({
  declarations: [
    ManagedErrorDirective,
    UserDetailComponent,
    DisplayManagedErrorComponent,
    ConfirmationDialogComponent,
    ChangePasswordDialogComponent,
    FileSizePipe,

    SecureIsAdministratorDirective,
    SecureIsUploaderDirective,
    SecureIsConsumerDirective,
    SecureIsAuthenticatedDirective
  ],
  providers: [
    ConfirmationDialogService,
    ChangePasswordDialogService
  ],
  imports: [
    CommonModule,

    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

    ToastrModule.forRoot({
      timeOut: 8000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }),

    NgbModule,
    NgbModalModule
  ],
  exports: [
    CommonModule,

    FormsModule,
    ReactiveFormsModule,

    ManagedErrorDirective,
    UserDetailComponent,
    DisplayManagedErrorComponent,

    FileSizePipe,

    NgbModule,
    NgbModalModule,

    SecureIsAdministratorDirective,
    SecureIsUploaderDirective,
    SecureIsConsumerDirective,
    SecureIsAuthenticatedDirective
  ],
  entryComponents: [ConfirmationDialogComponent, ChangePasswordDialogComponent]
})
export class SharedModule {
}
