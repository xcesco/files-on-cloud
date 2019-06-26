import { Component, OnInit } from '@angular/core';
import {AbstractUserDetailComponent} from '../../../abstracts/user-detail-component';
import {Consumer} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {ConsumerService} from '../../../services/consumer.service';

@Component({
  selector: 'app-consumer-detail',
  templateUrl: './consumer-detail.component.html',
  styleUrls: ['./consumer-detail.component.scss']
})
export class ConsumerDetailComponent extends AbstractUserDetailComponent<Consumer, ConsumerService> implements OnInit {

  constructor(actr: ActivatedRoute, service: ConsumerService, location: Location, toastr: ToastrService) {
    super(actr, service, location, toastr);
  }

  ngOnInit(): void {
  }

}
