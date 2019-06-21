import {Component, OnInit} from '@angular/core';
import {Administrator} from '../../types/users';
import {map} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-admin-password',
  templateUrl: './admin-password.component.html',
  styleUrls: ['./admin-password.component.scss']
})
export class AdminPasswordComponent implements OnInit {

  passwordUrl: string;

  constructor(private actr: ActivatedRoute) {
    this.actr.data.pipe(map(data => data.passwordUrl)).subscribe((value: string) => {
      console.log('caricato', value);
      this.passwordUrl = value;
    });
  }

  ngOnInit() {
  }

}
