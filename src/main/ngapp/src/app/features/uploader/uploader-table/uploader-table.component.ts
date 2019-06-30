import {Component, OnInit} from '@angular/core';
import {Uploader} from '../../../types/users';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {UploaderService} from '../../../services/uploader.service';
import {AbstractUserTableComponent} from '../../user-table.abstract';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-uploader-table',
  templateUrl: './uploader-table.component.html',
  styleUrls: ['./uploader-table.component.scss']
})
export class UploaderTableComponent extends AbstractUserTableComponent<Uploader, UploaderService> implements OnInit {

  constructor(public authService: AuthService, private router: Router, actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              service: UploaderService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      service, toastr);

    // se è un consumer ed un solo uploader, allora va direttamente.
    if (authService.hasRoleConsumer() && this.list.length === 1) {
      this.onGotoFiles(this.list[0]);
    }
  }

  ngOnInit() {
    console.log('componentns --');
  }

  getLogoUrl(id: number): string {
    return `/api/v1/public/uploaders/${id}/logo`;
  }

  onGotoFiles(user: Uploader) {
    this.router.navigate(['files'], {
      queryParams: {
        consumerId: this.authService.user.id,
        uploaderId: user.id
      }
    });
  }
}
