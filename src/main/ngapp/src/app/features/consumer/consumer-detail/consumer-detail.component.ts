import {Component, OnInit} from '@angular/core';
import {AbstractUserDetailComponent} from '../../user-detail.abstract';
import {Consumer} from '../../../types/users';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {ConsumerService} from '../../../services/consumer.service';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-consumer-detail',
  templateUrl: './consumer-detail.component.html',
  styleUrls: ['./consumer-detail.component.scss']
})
export class ConsumerDetailComponent extends AbstractUserDetailComponent<Consumer, ConsumerService> implements OnInit {

  constructor(authService: AuthService, actr: ActivatedRoute, router: Router, service: ConsumerService, location: Location, toastr: ToastrService) {
    super(authService, actr, router, service, location, toastr);
  }

  ngOnInit(): void {
  }

}
