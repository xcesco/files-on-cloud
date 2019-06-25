import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Administrator} from '../types/users';
import {AbstractUserService} from './abstract-user.service';
import {AdminModule} from '../admin/admin.module';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends AbstractUserService<Administrator> {

  constructor(httpClient: HttpClient) {
    super(httpClient);

    this.baseUrl = 'administrators';
  }

}
