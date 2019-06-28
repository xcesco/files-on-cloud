import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {ROUTE_PARAM_FILE_UUID, ROUTE_PARAM_USER_ID} from '../app-routing.costant';
import {CloudFileService} from '../services/cloud-file.service';
import {CloudFile} from '../types/files';

@Injectable({providedIn: 'root'})
export class CloudFileReadResolver implements Resolve<CloudFile> {

  constructor(private service: CloudFileService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CloudFile> {
    console.log('recupera elenco');
    const uuid = route.params[ROUTE_PARAM_FILE_UUID];
    return this.service.get(uuid);
  }
}
