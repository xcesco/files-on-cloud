import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';

@Injectable({providedIn: 'root'})
export class AdminReadResolver implements Resolve<Administrator> {

  constructor(private adminService: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Administrator> {
    console.log('recupera elenco');
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.adminService.adminsGet(utenteId);
  }
}
