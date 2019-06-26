import {Component, OnInit} from '@angular/core';
import {Consumer} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {ConsumerService} from '../../../services/consumer.service';
import {AbstractUserTableComponent} from '../../../abstracts/user-table-component';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-consumer-table',
  templateUrl: './consumer-table.component.html',
  styleUrls: ['./consumer-table.component.scss']
})
export class ConsumerTableComponent extends AbstractUserTableComponent<Consumer, ConsumerService> implements OnInit {

  constructor(actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              service: ConsumerService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      service, toastr);
  }

  ngOnInit() {
    console.log('componentns --');
  }
}

