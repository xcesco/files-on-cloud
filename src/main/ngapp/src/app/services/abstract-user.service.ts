import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Administrator} from '../types/users';
import {environment} from '../../environments/environment';

export class AbstractUserService<E> {

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
  get(id: number): Observable<Administrator> {
    return this.httpClient.get<Administrator>(environment.API_URL + this.baseUrl + `/${id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  save(bean: Administrator): Observable<Administrator> {
    return this.httpClient.put<Administrator>(environment.API_URL + this.baseUrl + `/${bean.id}`, bean);
  }

  /**
   * Cancella un admin mediante il suo id
   * @param id  id dell'utente
   */
  delete(bean: Administrator): Observable<Administrator> {
    return this.httpClient.delete<Administrator>(environment.API_URL + this.baseUrl + `/${bean.id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  changePasswordUrl(id: number): Observable<string> {
    return this.httpClient.get<string>(environment.API_URL + this.baseUrl + `/${id}/change-password`);
  }
}
