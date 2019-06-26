import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Consumer, Uploader} from '../types/users';
import {ConsumerService} from '../services/consumer.service';
import {Observable} from 'rxjs';
import {UploaderService} from '../services/uploader.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderCreateResolver implements Resolve<Uploader> {

  constructor(private service: UploaderService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Uploader> {
    return this.service.create();
  }
}
