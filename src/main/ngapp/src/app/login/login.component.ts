import {Component, OnInit} from '@angular/core';
import {AuthProvider} from '@firebaseui/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  providers = AuthProvider;

  constructor() {
  }

  ngOnInit() {
  }

  printUser(event) {
    console.log(event);
  }

  printError() {
    console.error();
  }

}
