import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Administrator} from '../../types/users';
import {map} from 'rxjs/operators';


@Component({
  selector: 'app-admins-table',
  templateUrl: './admins-table.component.html',
  styleUrls: ['./admins-table.component.scss']
})
export class AdminsTableComponent implements OnInit {

  list: Administrator[];

  constructor(private actr: ActivatedRoute) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: Administrator[]) => {
      console.log('caricato', value);
      this.list = value;
    });
  }

  ngOnInit() {
    console.log('componentns --');
  }

}
