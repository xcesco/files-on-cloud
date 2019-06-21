import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';


import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {Consumer} from '../types/users';
import {ConsumerService} from '../services/consumer.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumerGetResolverimplements implements Resolve<Consumer> {

  constructor(private service: ConsumerService) {

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Consumer> {
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.service.get(utenteId);
  }
}
