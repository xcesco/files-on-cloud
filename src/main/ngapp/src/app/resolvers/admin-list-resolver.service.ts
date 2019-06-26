import {Injectable} from '@angular/core';
import {Administrator} from '../types/users';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AdminService} from '../services/admin.service';

@Injectable({
  providedIn: 'root'
})
export class AdminListResolver implements Resolve<Administrator[]> {

  constructor(private service: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Administrator[]> {
    console.log('recupera elenco');
    // route.params['name'];
    return this.service.findAll();
  }
}
