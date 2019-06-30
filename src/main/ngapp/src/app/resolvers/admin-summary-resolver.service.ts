import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {AdminReportItem, DetailedSummary, Summary} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminReportResolver implements Resolve<AdminReportItem[]> {

  constructor(private adminService: AdminService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdminReportItem[]> {
    return this.adminService.getReport(null, null);
  }
}
