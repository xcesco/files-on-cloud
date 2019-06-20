import {Component, OnInit} from '@angular/core';
import {Administrator} from '../../types/users';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-admins-detail',
  templateUrl: './admins-detail.component.html',
  styleUrls: ['./admins-detail.component.scss']
})
export class AdminsDetailComponent implements OnInit {

  detail: Administrator;

  constructor(private actr: ActivatedRoute) {
    this.actr.data.pipe(map(data => data.detail)).subscribe((value: Administrator) => {
      console.log('caricato', value);
      this.detail = value;
    });
  }

  ngOnInit() {
    console.log('componentns --');
  }

}
