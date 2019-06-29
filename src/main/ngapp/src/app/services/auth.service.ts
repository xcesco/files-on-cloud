import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {AngularFireAuth} from '@angular/fire/auth';
import {ToastrService} from 'ngx-toastr';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {JwtUser} from '../types/auth.type';
import {isNotBlank} from '../shared/utils/utils';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper: JwtHelperService = new JwtHelperService();

  protected baseUrl = 'public/';

  protected user: JwtUser = null;
  public token = null;

  constructor(public  afAuth: AngularFireAuth, public  router: Router, private toastr: ToastrService, protected httpClient: HttpClient) {
    this.afAuth.authState.subscribe(user => {
      if (user) {
        localStorage.setItem('user', JSON.stringify(user));
      } else {
        localStorage.setItem('user', null);
      }
    });

    this.afAuth.idToken.subscribe(value => {
      console.log('nuovo token!!!');

      if (isNotBlank(value)) {
        // allora inviio, altrimenti non faccio nulla
        this.authenticateToBackend(value).pipe(map(result => result.token)).subscribe(token => {
          console.log('login');
          console.log('-->', token);
          console.log('-->', this.jwtHelper.decodeToken(token));

          this.token = token;
          sessionStorage.setItem('token', token);
          this.user = this.jwtHelper.decodeToken(token);
        });
      } else {
        console.log('.. ma non faccio niente');
        sessionStorage.removeItem('token');
      }

    });
  }


  authenticateToBackend(tokenValue: string): Observable<{ token: string }> {
    return this.httpClient.get<{ token: string }>(environment.API_URL + this.baseUrl + 'token', {
      params: {token: tokenValue}
    });
  }


  async login(email: string, password: string) {
    try {
      await this.afAuth.auth.signInWithEmailAndPassword(email, password);
    } catch (e) {
      this.toastr.error('Invalid credential', 'Login failed');
    }
    // console.log('autenticato', user);
    // this.router.navigate(['admins/list']);

  }

  async logout() {
    await this.afAuth.auth.signOut();
    localStorage.removeItem('user');
    this.router.navigate(['admin/login']);
  }

  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user'));
    return user !== null;
  }

  isAuthenticated() {
    return this.user !== null;
  }
}
