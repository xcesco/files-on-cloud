import {User} from '../types/users';
import {AbstractUserService} from '../services/abstract-user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {map} from 'rxjs/operators';
import {isNotBlank} from '../shared/utils/utils';
import {HttpErrorResponse} from '@angular/common/http';
import {Input} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {NotificationService} from '../services/notification.service';

export class AbstractUserDetailComponent<E extends User, S extends AbstractUserService<E>> {

  constructor(protected notificationService: NotificationService, protected authService: AuthService, protected actr: ActivatedRoute, protected router: Router, protected service: S, protected location: Location, protected toastr: ToastrService) {
    this.actr.data.pipe(map(data => data.detail)).subscribe((value: E) => {
      console.log('caricato', value);
      this.user = value;
    });
  }

  get isJustCreated(): boolean {
    return !this.alreadyExists;
  }

  get alreadyExists(): boolean {
    return isNotBlank(this.user.id);
  }

  @Input() signupMode = false;

  user: E;

  goBack(): void {
    console.log('go back');
    this.location.back();
  }

  onSave(user: E) {
    console.log('faccio save', user);

    if (this.signupMode) {
      this.service.saveForSignup(user).subscribe(data => {
        console.log('datao salvato');
        this.toastr.success(`User is registered!`, 'System information');
        this.router.navigate(['/login']);
      }, (error: HttpErrorResponse) => {
        // this.toastr.success(`User was correctly updated!`, 'User save!');
        this.toastr.error('Something went wrong during signup!', 'System information');
      }, () => {
        this.notificationService.notifyOperation({status: 'STOP'});
      });

    } else {
      this.service.save(user).subscribe(data => {
        if (this.authService.user.id === user.id) {
          console.log('Ricarico dati utente');
          this.authService.notifyDisplayUpdate(user.displayName);
        }

        this.toastr.success(`User is updated!`, 'System information');
        this.location.back();
      }, (error: HttpErrorResponse) => {
        this.toastr.error('An error occurred!', 'System information');
        console.log('errorere', error.error);
        this.notificationService.notifyOperation({status: 'STOP'});
      });
    }

  }
}
