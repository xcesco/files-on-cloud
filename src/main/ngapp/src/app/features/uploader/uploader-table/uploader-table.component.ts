import {Component, OnInit} from '@angular/core';
import {Uploader} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {UploaderService} from '../../../services/uploader.service';
import {AbstractUserTableComponent} from '../../user-table.abstract';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-uploader-table',
  templateUrl: './uploader-table.component.html',
  styleUrls: ['./uploader-table.component.scss']
})
export class UploaderTableComponent extends AbstractUserTableComponent<Uploader, UploaderService> implements OnInit {

  constructor(actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              service: UploaderService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      service, toastr);
  }

  ngOnInit() {
    console.log('componentns --');
  }

  getLogoUrl(id: number): string {
    return `/api/v1/public/uploaders/${id}/logo`;//?t=` + (new Date()).getTime();
  }
}
