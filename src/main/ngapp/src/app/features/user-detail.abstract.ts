import {User} from '../types/users';
import {AbstractUserService} from '../services/abstract-user.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {map} from 'rxjs/operators';
import {isNotBlank} from '../shared/utils/utils';
import {HttpErrorResponse} from '@angular/common/http';

export class AbstractUserDetailComponent<E extends User, S extends AbstractUserService<E>> {

  constructor(protected actr: ActivatedRoute, protected service: S, protected location: Location, protected toastr: ToastrService) {
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

  user: E;

  goBack(): void {
    console.log('go back');
    this.location.back();
  }

  onSave(user: E) {
    console.log('faccio save', user);
    this.service.save(user).subscribe(data => {
      console.log('datao salvoat');
      this.toastr.success(`User is updated!`, 'System information');
      this.location.back();
    }, (error: HttpErrorResponse) => {
      // this.toastr.success(`User was correctly updated!`, 'User save!');
      this.toastr.error('Something went wrong during save operation!', 'System information');
      console.log('errorere', error.error);
    });
  }
}
