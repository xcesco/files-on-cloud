import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {auth} from 'firebase/app';
import {AngularFireAuth} from '@angular/fire/auth';
import {User} from 'firebase';
import UserCredential = firebase.auth.UserCredential;
import {ToastrService} from 'ngx-toastr';
import {HttpClient} from '@angular/common/http';
import {CloudFile} from '../types/files';
import {Observable} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper: JwtHelperService = new JwtHelperService();

  protected baseUrl = 'auth/';
  user: User;

  constructor(public  afAuth: AngularFireAuth, public  router: Router, private toastr: ToastrService, protected httpClient: HttpClient) {
    this.afAuth.authState.subscribe(user => {
      if (user) {
        this.user = user;
        localStorage.setItem('user', JSON.stringify(this.user));
      } else {
        localStorage.setItem('user', null);
      }
    });

    this.afAuth.idToken.subscribe(value => {
      console.log('nuovo token!!!');

      this.getUser(value).pipe(map(result => result.token)).subscribe(token => {
        console.log('-->', token);
        console.log('-->', this.jwtHelper.decodeToken(token));
      });
    });
  }

  getUser(tokenValue: string): Observable<{token: string}> {
    return this.httpClient.get<{token: string}>(environment.API_URL + this.baseUrl + 'token', {
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
}
