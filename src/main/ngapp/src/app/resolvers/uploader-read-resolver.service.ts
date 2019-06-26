import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Uploader} from '../types/users';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {UploaderService} from '../services/uploader.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderReadResolver implements Resolve<Uploader> {

  constructor(private service: UploaderService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Uploader> {
    console.log('recupera elenco');
    const utenteId = route.params[ROUTE_PARAM_USER_ID];
    return this.service.get(utenteId);
  }
}
