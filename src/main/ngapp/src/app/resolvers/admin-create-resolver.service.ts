import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminCreateResolver implements Resolve<Administrator> {

  constructor(private adminService: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Administrator> {
    return this.adminService.create();
  }
}
