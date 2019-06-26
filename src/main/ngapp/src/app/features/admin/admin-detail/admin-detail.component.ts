import {Component, OnInit} from '@angular/core';
import {Administrator, User} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {AdminService} from '../../../services/admin.service';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {AbstractUserDetailComponent} from '../../../abstracts/user-detail-component';

@Component({
  selector: 'app-admins-detail',
  templateUrl: './admin-detail.component.html',
  styleUrls: ['./admin-detail.component.scss']
})
export class AdminDetailComponent extends AbstractUserDetailComponent<Administrator, AdminService> implements OnInit {

  constructor(actr: ActivatedRoute, service: AdminService, location: Location, toastr: ToastrService) {
   super(actr, service, location, toastr);
  }

  ngOnInit(): void {
  }

}
