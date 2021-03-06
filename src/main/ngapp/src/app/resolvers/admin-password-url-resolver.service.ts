import {Injectable} from '@angular/core';
import {ChangePasswordUrl} from '../types/users';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {AdminService} from '../services/admin.service';

@Injectable({
  providedIn: 'root'
})
export class AdminPasswordUrlResolver implements Resolve<ChangePasswordUrl> {

  constructor(private service: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChangePasswordUrl> {
    console.log('recupera password url');
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.service.changePasswordUrl(utenteId);
  }
}
