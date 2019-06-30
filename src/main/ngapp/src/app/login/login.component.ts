import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginWithError = false;
  loading = false;


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

  login(username: string, password: string) {
    this.loading = true;
    this.loginWithError = false;
    this.authService.login(username, password).then(result => {
      if (!result) {
        this.loginWithError = true;
      } else {
        this.loginWithError = false;
      }
    }).finally(() =>
      this.loading = false);
  }
}
