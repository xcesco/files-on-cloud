import {Component, OnInit} from '@angular/core';
import {Administrator} from '../../../types/users';
import {ActivatedRoute, Router} from '@angular/router';
import {AdminService} from '../../../services/admin.service';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {AbstractUserDetailComponent} from '../../user-detail.abstract';

@Component({
  selector: 'app-admins-detail',
  templateUrl: './admin-detail.component.html',
  styleUrls: ['./admin-detail.component.scss']
})
export class AdminDetailComponent extends AbstractUserDetailComponent<Administrator, AdminService> implements OnInit {

  constructor(actr: ActivatedRoute, router: Router, service: AdminService, location: Location, toastr: ToastrService) {
    super(actr, router, service, location, toastr);
  }

  ngOnInit(): void {
  }

}
