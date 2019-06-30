import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {DetailedSummary, Summary} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminReportResolver implements Resolve<DetailedSummary[]> {

  constructor(private adminService: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DetailedSummary[]> {
    return this.adminService.getSummary(null, null);
  }
}
