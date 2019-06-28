import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../../services/auth.service';
import {ROUTE_LOGIN} from '../../app-routing.costant';


@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.performCheck(childRoute, state);
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.performCheck(next, state);
  }


  /**
   * this is method does the checking for us, according to the below process
   * 1. check if the user is authenticated, if so check if is time to refresh token the return the observable
   * so our guard can resolve it, since the retrieve method is already handling the response with `do` we just map this
   * to true default so next view can check and see if the new token is valid or not
   *
   * 2. if the above from 1 pass through without returning it means it was false all the way
   * so we handle it by passing a message and updating the redirectUrl so users can continue where they left of
   * after authentication
   * @param next
   * @param state
   * @returns {any}
   */
  private performCheck(next: ActivatedRouteSnapshot,
                       state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {

    // console.log('dsdsdsd');
    if (this.authService.isAuthenticated()) {
      return true;
    }

    // this.authService.setRedirectUrl(state.url);
    // this.authService.message = 'Please login to continue';
    // navigate to login page
    this.router.navigate([ROUTE_LOGIN]);
    return false;
  }
}

