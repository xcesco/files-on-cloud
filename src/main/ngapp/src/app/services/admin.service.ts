import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Administrator} from '../types/users';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) {

  }

  /**
   * Elenco di tutti gli admin
   */
  findAllAdmins(): Observable<Administrator[]> {
    return this.httpClient.get<Administrator[]>(environment.API_URL + 'administrators/');
  }

  /**
   * Prepara un admin di default, pronto per essere salvato
   */
  adminsNew(): Observable<Administrator> {
    return this.httpClient.get<Administrator>(environment.API_URL + 'administrators/new');
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  adminsGet(id: number): Observable<Administrator> {
    return this.httpClient.get<Administrator>(environment.API_URL + `administrators/{id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  adminsSave(bean: Administrator): Observable<Administrator> {
    return this.httpClient.put<Administrator>(environment.API_URL + `administrators/{bean.id}`, bean);
  }

  /**
   * Cancella un admin mediante il suo id
   * @param id  id dell'utente
   */
  adminsDelete(bean: Administrator): Observable<Administrator> {
    return this.httpClient.delete<Administrator>(environment.API_URL + `administrators/{bean.id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  adminsChangePassword(bean: Administrator): Observable<string> {
    return this.httpClient.get<string>(environment.API_URL + `administrators/{bean.id}/change-password`);
  }
}
