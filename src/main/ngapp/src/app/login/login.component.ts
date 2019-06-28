import {Component, OnInit} from '@angular/core';
import {AuthProvider} from '@firebaseui/ng-bootstrap';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  providers = AuthProvider;

  constructor(private  authService: AuthService) {
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
