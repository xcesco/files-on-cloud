import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {AngularFireAuth} from '@angular/fire/auth';
import {ToastrService} from 'ngx-toastr';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {JwtUser} from '../types/auth.type';
import {isNotBlank} from '../shared/utils/utils';
import {Consumer} from '../types/users';

@Injectable()
export class AuthService {
  userLoggedSubject: Subject<JwtUser> = new Subject<JwtUser>();
  private jwtHelper: JwtHelperService = new JwtHelperService();
  private baseUrl = 'public/';
  user: JwtUser = null;
  private authToken: string = null;

  constructor(public  afAuth: AngularFireAuth, public  router: Router, private toastr: ToastrService, protected httpClient: HttpClient) {
    this.authToken = sessionStorage.getItem('token');
    if (isNotBlank(this.authToken)) {
      console.log('reload');
      this.user = this.jwtHelper.decodeToken(this.authToken);
      this.userLoggedSubject.next({...this.user});

      this.authToken = sessionStorage.getItem('token');

      this.redirectToHome();
    }

    /*
        this.afAuth.idToken.subscribe(value => {
          console.log('nuovo token!!!');

          if (isNotBlank(value)) {
            this.authenticateToBackend(value).pipe(map(result => result.token)).subscribe(token => {
              console.log('login');
              console.log('-->', token);
              console.log('-->', this.jwtHelper.decodeToken(token));

              this.authToken = token;
              sessionStorage.setItem('token', token);

              this.user = this.jwtHelper.decodeToken(token);
              this.userLoggedSubject.next({...this.user});

              this.redirectToHome();
            });
          } else {
            console.log('.. ma non faccio niente');
            sessionStorage.removeItem('token');
            this.userLoggedSubject.next(null);
          }

        });*/
  }

  hasRoleAdministrator(): boolean {
    return this.user !== null && this.user.roles.length === 1 && this.user.roles[0] === 'ROLE_ADMINISTRATOR';
  }

  hasRoleAdministratorOrUploader(): boolean {
    return this.user !== null && this.user.roles.length === 1 && (this.user.roles[0] === 'ROLE_ADMINISTRATOR' || this.user.roles[0] === 'ROLE_UPLOADER');
  }

  hasRoleUploader(): boolean {
    return this.user !== null && this.user.roles.length === 1 && this.user.roles[0] === 'ROLE_UPLOADER';
  }

  hasRoleConsumer(): boolean {
    return this.user !== null && this.user.roles.length === 1 && this.user.roles[0] === 'ROLE_CONSUMER';
  }

  hasRoleUploaderOrConsumer(): boolean {
    return this.user !== null && this.user.roles.length === 1 && (this.user.roles[0] === 'ROLE_CONSUMER' || this.user.roles[0] === 'ROLE_UPLOADER');
  }

  get token(): string {
    return this.authToken;
  }

  authenticateToBackend(tokenValue: string): Observable<{ token: string }> {
    return this.httpClient.get<{ token: string }>(environment.API_URL + this.baseUrl + 'token', {
      params: {token: tokenValue}
    });
  }


  async login(email: string, password: string): Promise<boolean> {
    try {
      const user = await this.afAuth.auth.signInWithEmailAndPassword(email, password);
      this.afAuth.idToken.subscribe(value => {
        console.log('nuovo token!!!');

        if (isNotBlank(value)) {
          this.authenticateToBackend(value).pipe(map(result => result.token)).subscribe(token => {
            console.log('login');
            console.log('-->', token);
            console.log('-->', this.jwtHelper.decodeToken(token));

            this.authToken = token;
            sessionStorage.setItem('token', token);

            this.user = this.jwtHelper.decodeToken(token);
            this.userLoggedSubject.next({...this.user});

            this.redirectToHome();
          });
        } else {
          console.log('.. ma non faccio niente');
          sessionStorage.removeItem('token');
          this.userLoggedSubject.next(null);
        }

      });

      return true;
    } catch (e) {
      this.toastr.error('Invalid credential', 'Login failed');
      return false;
    }
  }

  async logout() {
    await this.afAuth.auth.signOut();

    this.user = null;

    this.authToken = null;
    sessionStorage.removeItem('token');


    this.router.navigate(['login']);
    this.userLoggedSubject.next(null);
  }

  /**
   * Prepara un admin di default, pronto per essere salvato
   */
  createConsumer(): Observable<Consumer> {
    return this.httpClient.get<Consumer>(environment.API_URL + 'public/consumers/new');
  }

  get isLoggedIn(): boolean {
    return this.user !== null;
  }

  isAuthenticated() {
    return this.user !== null;
  }

  /**
   * Per evitare di rifare reload della pagina o refresh del token , quando cambia il diplay emuliamo cambio utente
   */
  notifyDisplayUpdate(displayName: string) {
    const temp = {...this.user};
    temp.displayName = displayName;
    this.userLoggedSubject.next(temp);
  }


  private redirectToHome(): void {
    if (this.hasRoleAdministrator()) {
      this.router.navigate(['administrators/list']);
    } else if (this.hasRoleUploader()) {
      this.router.navigate(['consumers']);
    } else if (this.hasRoleConsumer()) {
      this.router.navigate(['uploaders']);
    }
  }
}
