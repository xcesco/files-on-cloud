import { Component, OnInit } from '@angular/core';
import {AbstractUserDetailComponent} from '../../../abstracts/user-detail-component';
import {Uploader} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {UploaderService} from '../../../services/uploader.service';

@Component({
  selector: 'app-uploader-detail',
  templateUrl: './uploader-detail.component.html',
  styleUrls: ['./uploader-detail.component.scss']
})
export class UploaderDetailComponent extends AbstractUserDetailComponent<Uploader, UploaderService> implements OnInit {

  constructor(actr: ActivatedRoute, service: UploaderService, location: Location, toastr: ToastrService) {
    super(actr, service, location, toastr);
  }

  ngOnInit(): void {
  }

}
