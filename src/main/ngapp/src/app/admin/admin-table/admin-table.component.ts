import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Administrator} from '../../types/users';
import {map} from 'rxjs/operators';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-admins-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss']
})
export class AdminTableComponent implements OnInit {

  list: Administrator[];

  constructor(private actr: ActivatedRoute, private modalService: NgbModal) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: Administrator[]) => {
      console.log('caricato', value);
      this.list = value;
    });
  }

  ngOnInit() {
    console.log('componentns --');
  }

}
