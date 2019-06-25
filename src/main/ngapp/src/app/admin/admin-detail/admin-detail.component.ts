import {Component, OnInit} from '@angular/core';
import {Administrator} from '../../types/users';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {AdminService} from '../../services/admin.service';
import {HttpErrorResponse} from '@angular/common/http';
import {isNotBlank} from '../../shared/utils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-admins-detail',
  templateUrl: './admin-detail.component.html',
  styleUrls: ['./admin-detail.component.scss']
})
export class AdminDetailComponent implements OnInit {

  constructor(private actr: ActivatedRoute, private service: AdminService, private location: Location) {
    this.actr.data.pipe(map(data => data.detail)).subscribe((value: Administrator) => {
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

  user: Administrator;

  ngOnInit(): void {
  }

  onSave(user: Administrator) {
    console.log('faccio save', user);
    this.service.save(user).subscribe(data => {
      console.log('datao salvoat');
      this.location.back();
    }, (error: HttpErrorResponse) => {

      console.log('errorere', error.error);
    });
  }

}
