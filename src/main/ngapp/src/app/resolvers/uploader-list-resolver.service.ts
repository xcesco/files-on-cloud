import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator, Uploader} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {UploaderService} from '../services/uploader.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderListResolver implements Resolve<Uploader[]> {

  constructor(private service: UploaderService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Uploader[]> {
    console.log('recupera elenco');
    return this.service.findAll();
  }
}
