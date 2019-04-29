import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AngularFireAuth} from '@angular/fire/auth';
import * as firebase from 'firebase/app';
import {Router} from '@angular/router';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';

/**
 * Gestisce l'utente autenticato con Firebase.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private user: Observable<firebase.User>;
  private userDetails: firebase.User = null;
  private readonly URL_ADMIN_TOKEN: string;

  constructor(private _firebaseAuth: AngularFireAuth, private router: Router, private httpClient: HttpClient) {
    this.URL_ADMIN_TOKEN = environment.API_URL + 'token';
    this.user = _firebaseAuth.authState;

    _firebaseAuth.idToken.subscribe(token => {
      console.log('sssss', token);
      this.executeToken(token).subscribe(value => {
                   console.log('9999999999', value);
                 });
    });
    // this.user.subscribe(
    //   (user) => {
    //     if (user) {
    //       this.userDetails = user;
    //       console.log('oooo', this.userDetails);
    //         this.executeToken(this.userDetails).subscribe(token => {
    //           console.log('9999999999', token);
    //         });
    //     } else {
    //       this.userDetails = null;
    //     }
    //   }
    // );
  }

  private executeToken(idToken: string): Observable<string> {
    const params = new HttpParams().set('token', idToken);

    return this.httpClient.get<string>(this.URL_ADMIN_TOKEN, { params : params });
  }

  signInWithTwitter() {
    return this._firebaseAuth.auth.signInWithPopup(
      new firebase.auth.TwitterAuthProvider()
    );
  }

  signInWithFacebook() {
    return this._firebaseAuth.auth.signInWithPopup(
      new firebase.auth.FacebookAuthProvider()
    );
  }

  signInWithGoogle() {
    return this._firebaseAuth.auth.signInWithPopup(
      new firebase.auth.GoogleAuthProvider()
    );
  }

  isLoggedIn() {
    if (this.userDetails == null) {
      return false;
    } else {
      return true;
    }
  }

  logout() {


    this._firebaseAuth.auth.signOut()
      .then((res) => this.router.navigate(['/']));
  }
}


