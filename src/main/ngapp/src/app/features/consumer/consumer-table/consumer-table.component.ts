import {Component, OnInit} from '@angular/core';
import {Consumer, Uploader} from '../../../types/users';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {ConsumerService} from '../../../services/consumer.service';
import {AbstractUserTableComponent} from '../../user-table.abstract';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-consumer-table',
  templateUrl: './consumer-table.component.html',
  styleUrls: ['./consumer-table.component.scss']
})
export class ConsumerTableComponent extends AbstractUserTableComponent<Consumer, ConsumerService> implements OnInit {

  constructor(public authService: AuthService, private router: Router, actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              service: ConsumerService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      service, toastr);
  }

  ngOnInit() {
    console.log('componentns --');
  }

  onGotoFiles(user: Uploader) {
    this.router.navigate(['files'], {
      queryParams: {
        consumerId: user.id,
        uploaderId: this.authService.user.id
      }
    });
  }
}

