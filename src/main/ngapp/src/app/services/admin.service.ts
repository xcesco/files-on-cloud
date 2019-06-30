import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Administrator, DetailedSummary, Summary} from '../types/users';
import {AbstractUserService} from './abstract-user.service';
import {environment} from '../../environments/environment';
import * as moment from 'moment';
import {isNotBlank} from '../shared/utils/utils';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends AbstractUserService<Administrator> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'secured/admins');
  }

  /**
   *
   * @param dataDal
   * @param dataAl
   */
  getSummary(dataDal: Date = null, dataAl: Date = null): Observable<DetailedSummary[]> {
    let params = {};

    if (isNotBlank(dataDal) && isNotBlank(dataAl)) {
      params = {
        dataDal: dataDal ? moment(dataDal).format('YYYY-MM-DD') : null,
        dataAl: dataAl ? moment(dataAl).format('YYYY-MM-DD') : null
      };
    }

    return this.httpClient.get<DetailedSummary[]>(environment.API_URL + this.baseUrl + '/detailed-summary', {params});
  }
}
