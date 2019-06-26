import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Consumer, Uploader} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {ConsumerService} from '../services/consumer.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumerListResolver implements Resolve<Consumer[]> {

  constructor(private service: ConsumerService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Consumer[]> {
    return this.service.findAll();
  }
}
