import {Component, OnInit} from '@angular/core';
import {Administrator} from '../../types/users';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-admins-detail',
  templateUrl: './admin-detail.component.html',
  styleUrls: ['./admin-detail.component.scss']
})
export class AdminDetailComponent implements OnInit {

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
