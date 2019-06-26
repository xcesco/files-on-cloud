import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Administrator, User} from '../../../types/users';
import {map} from 'rxjs/operators';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {AdminService} from '../../../services/admin.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {AbstractUserTableComponent} from '../../user-table.abstract';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-admins-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss']
})
export class AdminTableComponent extends AbstractUserTableComponent<Administrator, AdminService> implements OnInit {

  constructor(actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              adminService: AdminService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      adminService, toastr);
  }

  ngOnInit() {
    console.log('componentns --');
  }

}
