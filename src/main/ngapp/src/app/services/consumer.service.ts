import {Injectable} from '@angular/core';
import {Consumer} from '../types/users';
import {HttpClient} from '@angular/common/http';
import {AbstractUserService} from './abstract-user.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumerService extends AbstractUserService<Consumer> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'secured/consumers');

  }

}
