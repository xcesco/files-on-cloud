import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminReadResolver implements Resolve<Administrator> {

  constructor(private adminService: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Administrator> {
    console.log('recupera elenco');
    const utenteId = route.params.adminId as number;
    return this.adminService.adminsGet(utenteId);
  }
}
