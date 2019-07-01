import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../../services/auth.service';
import {ROUTE_LOGIN} from '../../app-routing.costant';


@Injectable()
export class AuthAdministratorGuard implements CanActivate, CanActivateChild {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.performCheck(childRoute, state);
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.performCheck(next, state);
  }

  private performCheck(next: ActivatedRouteSnapshot,
                       state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {

    // console.log('dsdsdsd');
    if (this.authService.isAuthenticated() && this.authService.hasRoleAdministrator()) {
      return true;
    }

    // la navigazione viene fatta dentro il logoutß
    // per sicurezza lo facciamo uscire
    this.authService.logout().then(() => {
      console.log('faccio logout forzato');
    });

    // navigate to login page
    return false;
  }
}

