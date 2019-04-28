import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AdminActor} from '../types/actors';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ActorsService {

  private readonly URL_ADMIN_FIND_ALL: string;

  constructor(private http: HttpClient) {
    this.URL_ADMIN_FIND_ALL = environment.API_URL + 'administrators';
  }

  /**
   * Elenco di tutti gli admin dell'app
   */
  findAllAdmins(): Observable<AdminActor[]> {
    return this.http.get<AdminActor[]>(this.URL_ADMIN_FIND_ALL);
  }

}
