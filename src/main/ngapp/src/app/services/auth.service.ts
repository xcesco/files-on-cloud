import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {auth} from 'firebase/app';
import {AngularFireAuth} from '@angular/fire/auth';
import {User} from 'firebase';
import UserCredential = firebase.auth.UserCredential;
import {ToastrService} from 'ngx-toastr';
import {HttpClient} from '@angular/common/http';
import {CloudFile} from '../types/files';
import {Observable, Subject} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public userSubject = new Subject<'LOGIN' | 'LOGOUT'>();

  private jwtHelper: JwtHelperService = new JwtHelperService();

  protected baseUrl = 'auth/';
  user: User;

  public get token(): string {
    return sessionStorage.getItem(environment.JWT_NAME);
  }

  public isAuthenticated(): boolean {
    return !this.jwtHelper.isTokenExpired(this.token);
  }
/*
  login(jwt: string) {
    sessionStorage.setItem(environment.JWT_NAME, jwt);
    this.userSubject.next('LOGIN');
  }

  logout() {
    sessionStorage.removeItem(environment.JWT_NAME);
    this.userSubject.next('LOGOUT');
  }*/

  constructor(public  afAuth: AngularFireAuth, public  router: Router, private toastr: ToastrService, protected httpClient: HttpClient) {
    this.afAuth.authState.subscribe(user => {
      if (user) {
        this.user = user;
        localStorage.setItem('user', JSON.stringify(this.user));
      } else {
        localStorage.setItem('user', null);
      }
    });

    this.afAuth.idToken.subscribe(token => {
      console.log('nuovo token!!!');

      this.getUser(token).subscribe(result => {
        console.log('-->', result);
        console.log('-->', this.jwtHelper.decodeToken(result));
      });
    });
  }

  getUser(tokenValue: string): Observable<string> {
    return this.httpClient.get<string>(environment.API_URL + this.baseUrl + 'token', {
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
