import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator, Uploader} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {UploaderService} from '../services/uploader.service';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderListResolver implements Resolve<Uploader[]> {

  constructor(private service: UploaderService, private authService: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Uploader[]> {
      console.log('recupera elenco');
      return this.service.findAll();
  }
}
