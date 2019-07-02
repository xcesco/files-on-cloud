import {User} from '../types/users';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../shared/components/change-password-dialog/change-password-dialog.service';
import {map} from 'rxjs/operators';
import {AbstractUserService} from '../services/abstract-user.service';
import {ToastrService} from 'ngx-toastr';

export class AbstractUserTableComponent<E extends User, S extends AbstractUserService<E>> {
  list: E[] = null;

  constructor(protected actr: ActivatedRoute, protected confirmationDialogService: ConfirmationDialogService,
              protected changePasswordDialogService: ChangePasswordDialogService,
              protected service: S,
              protected toastr: ToastrService) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: E[]) => {
      console.log('caricato', value);
      this.list = value;
      this.onListLoaded(this.list);
    });
  }

  onListLoaded(list: E[]) {
      console.log('faccio vecchio');
  }

  onDelete(user: E) {
    this.confirmationDialogService.confirm('Please confirm..', `Do you really want to delete user ${user.username}?`)
      .then((confirmed) => {
        console.log('User confirmed:', confirmed);
        this.toastr.info(`User was correctly deleted!`, 'User deletion');
        this.service.delete(user).subscribe(() => this.service.findAll().subscribe((newList: E[]) => {
          this.list = newList;
          console.log('User reloaded', newList);
        }));

      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  onChangePassword(user: E) {
    this.confirmationDialogService.confirm('Please confirm..', `Do you really want to change password for user ${user.username}?`)
      .then((confirmed) => {
        console.log('User confirmed:', confirmed);
        this.service.changePasswordUrl(user.id).subscribe(response => {
          this.changePasswordDialogService.confirm('Password change', response.url).finally(() => console.log('finito'));
        });
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }
}
