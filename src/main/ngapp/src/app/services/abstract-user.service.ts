import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Administrator, User} from '../types/users';
import {environment} from '../../environments/environment';

export class AbstractUserService<E extends User> {

  protected baseUrl;

  constructor(protected httpClient: HttpClient) {

  }

  /**
   * Elenco di tutti gli admin
   */
  findAll(): Observable<E[]> {
    return this.httpClient.get<E[]>(environment.API_URL + this.baseUrl + '/');
  }

  /**
   * Prepara un admin di default, pronto per essere salvato
   */
  create(): Observable<Administrator> {
    return this.httpClient.get<Administrator>(environment.API_URL + this.baseUrl + '/new');
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  get(id: number): Observable<E> {
    return this.httpClient.get<E>(environment.API_URL + this.baseUrl + `/${id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  save(bean: E): Observable<E> {
    return this.httpClient.put<E>(environment.API_URL + this.baseUrl + `/${bean.id}`, bean);
  }

  /**
   * Cancella un admin mediante il suo id
   * @param id  id dell'utente
   */
  delete(bean: E): Observable<E> {
    return this.httpClient.delete<E>(environment.API_URL + this.baseUrl + `/${bean.id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  changePasswordUrl(id: number): Observable<string> {
    return this.httpClient.get<string>(environment.API_URL + this.baseUrl + `/${id}/change-password`);
  }
}
