import {Injectable} from '@angular/core';
import {Administrator} from '../types/users';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AdminService} from '../services/admin.service';
import {CloudFile} from '../types/files';
import {CloudFileService} from '../services/cloud-file.service';

@Injectable({
  providedIn: 'root'
})
export class CloudFileListResolver implements Resolve<CloudFile[]> {

  constructor(private service: CloudFileService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CloudFile[]> {
    console.log('recupera elenco');
    // route.params['name'];
    return this.service.findAll();
  }
}

