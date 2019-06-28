import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Administrator} from '../types/users';
import {AdminService} from '../services/admin.service';
import {Observable} from 'rxjs';
import {CloudFileService} from '../services/cloud-file.service';
import {CloudFile, CloudFileData} from '../types/files';

@Injectable({
  providedIn: 'root'
})
export class CloudFileCreateResolver implements Resolve<CloudFileData> {

  constructor(private service: CloudFileService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CloudFileData> {

    return null;
  }
}
