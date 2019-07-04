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

  constructor(private authService: AuthService, private router: Router, actr: ActivatedRoute, confirmationDialogService: ConfirmationDialogService,
              changePasswordDialogService: ChangePasswordDialogService,
              service: UploaderService, toastr: ToastrService) {
    super(actr, confirmationDialogService,
      changePasswordDialogService,
      service, toastr);


  }

  ngOnInit() {
    if (this.authService.hasRoleConsumer() && this.list !== null && this.list.length === 1) {
      // se siamo un consumer, elenchiamo gli uploader per visualizzare poi l'elenco dei file.
      // se ne troviamo solo uno, andiamo direttamente sull'elenco dei file.
      this.onGotoFiles(this.list[0]);
    }

  }

  getLogoUrl(id: number): string {
    return `/api/v1/public/uploaders/${id}/logo`;
  }

  onGotoFiles(user: Uploader) {
    this.router.navigate(['files'], {
      queryParams: {
        consumerId: this.authService.user.id,
        uploaderId: user.id,
        uploaderDisplayName: user.displayName,
        allowGoBack: this.list.length > 1
      }
    });
  }
}
