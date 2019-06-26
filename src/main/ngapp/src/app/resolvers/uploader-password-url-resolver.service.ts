import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {ChangePasswordUrl} from '../types/users';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {UploaderService} from '../services/uploader.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderPasswordUrlResolver implements Resolve<ChangePasswordUrl> {

  constructor(private service: UploaderService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChangePasswordUrl> {
    console.log('recupera password url');
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.service.changePasswordUrl(utenteId);
  }
}
