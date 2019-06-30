import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ChangePasswordUrl, User} from '../types/users';
import {environment} from '../../environments/environment';
import {isNotBlank} from '../shared/utils/utils';

export class AbstractUserService<E extends User> {

  constructor(protected httpClient: HttpClient, protected baseUrl) {

  }

  /**
   * Elenco di tutti gli admin
   */
  findAll(): Observable<E[]> {
    console.log('findAll ', environment.API_URL + this.baseUrl + '/');
    return this.httpClient.get<E[]>(environment.API_URL + this.baseUrl + '/');
  }

  /**
   * Prepara un admin di default, pronto per essere salvato
   */
  create(): Observable<E> {
    console.log('create ', environment.API_URL + this.baseUrl + '/');
    return this.httpClient.get<E>(environment.API_URL + this.baseUrl + '/new');
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  get(id: number): Observable<E> {
    console.log('get ', environment.API_URL + this.baseUrl + '/');
    return this.httpClient.get<E>(environment.API_URL + this.baseUrl + `/${id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  save(bean: E): Observable<E> {
    console.log('save ', environment.API_URL + this.baseUrl + '/');
    if (isNotBlank(bean.id)) {
      return this.httpClient.put<E>(environment.API_URL + this.baseUrl + `/${bean.id}`, bean);
    } else {
      return this.httpClient.post<E>(environment.API_URL + this.baseUrl, bean);
    }

  }

  /**
   * Cancella un admin mediante il suo id
   * @param id  id dell'utente
   */
  delete(bean: E): Observable<E> {
    console.log('delete ', environment.API_URL + this.baseUrl + '/');
    return this.httpClient.delete<E>(environment.API_URL + this.baseUrl + `/${bean.id}`);
  }

  /**
   * Recupera un admin mediante il suo id
   * @param id  id dell'utente
   */
  changePasswordUrl(id: number): Observable<ChangePasswordUrl> {
    console.log('changePasswordUrl ', environment.API_URL + this.baseUrl + '/');
    return this.httpClient.get<ChangePasswordUrl>(environment.API_URL + this.baseUrl + `/${id}/change-password`);
  }

  /**
   * Temporaneamnte messo qua. Non mi piace granchè.
   * @param id  id dell'utente
   */
  saveForSignup(bean: E): Observable<E> {
    return this.httpClient.post<E>(environment.API_URL + 'public/consumers/', bean);
  }
}
