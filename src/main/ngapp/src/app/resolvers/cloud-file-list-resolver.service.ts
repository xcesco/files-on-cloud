import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {CloudFile} from '../types/files';
import {CloudFileService} from '../services/cloud-file.service';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CloudFileListResolver implements Resolve<CloudFile[]> {

  constructor(private authService: AuthService, private service: CloudFileService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CloudFile[]> {
    console.log('recupera elenco', route.queryParams);

    if (this.authService.hasRoleConsumer()) {
      // se consumer, deve per forza avere uploader e consumer (
      return this.service.findByUploaderAndConsumer(route.queryParams.uploaderId, route.queryParams.consumerId);
    } else {
      return this.service.findAll();
    }
  }
}

