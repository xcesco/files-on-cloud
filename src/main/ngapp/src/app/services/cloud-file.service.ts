import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {CloudFile} from '../types/files';

@Injectable({
  providedIn: 'root'
})
export class CloudFileService {

  constructor(protected httpClient: HttpClient) {

  }

  protected baseUrl = 'secured/files/';

  findAll(): Observable<CloudFile[]> {
    console.log('findAll ', environment.API_URL + this.baseUrl);
    return this.httpClient.get<CloudFile[]>(environment.API_URL + this.baseUrl);
  }

  delete(item: CloudFile): Observable<boolean> {
    return this.httpClient.delete<boolean>(environment.API_URL + this.baseUrl + `${item.uuid}`);
  }

  create(): Observable<CloudFile> {
    return this.httpClient.get<CloudFile>(environment.API_URL + this.baseUrl + `new`);
  }

  get(uuid: string): Observable<CloudFile> {
    return this.httpClient.get<CloudFile>(environment.API_URL + this.baseUrl + `${uuid}`);
  }

  /**
   * salva un file. Funziona solo per gli uploaders
   *
   * @param formData
   */
  save(formData: FormData): Observable<boolean> {
    return this.httpClient.post<boolean>(environment.API_URL + this.baseUrl, formData);
  }

  sendNotification(item: CloudFile): Observable<void> {
    return this.httpClient.get<void>(environment.API_URL + this.baseUrl + `${item.uuid}/notification/send`);
  }
}
