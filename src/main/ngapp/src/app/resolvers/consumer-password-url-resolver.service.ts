import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {ChangePasswordUrl} from '../types/users';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {ConsumerService} from '../services/consumer.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumerPasswordUrlResolver implements Resolve<ChangePasswordUrl> {

  constructor(private service: ConsumerService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChangePasswordUrl> {
    console.log('recupera password url');
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.service.changePasswordUrl(utenteId);
  }
}
