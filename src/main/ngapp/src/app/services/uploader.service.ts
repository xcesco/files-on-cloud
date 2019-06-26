import {Injectable} from '@angular/core';
import {Administrator, Consumer, Uploader} from '../types/users';
import {HttpClient} from '@angular/common/http';
import {AbstractUserService} from './abstract-user.service';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploaderService extends AbstractUserService<Uploader> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'uploaders');
  }

  uploadLogo(beanId: number, fileToUpload: File): Observable<boolean> {
    console.log('save ', environment.API_URL + this.baseUrl + '/');

    const formData = new FormData();
    formData.append('file', fileToUpload);

    return this.httpClient.patch<boolean>(environment.API_URL + this.baseUrl + `/${beanId}/logo`, formData);
  }
}
