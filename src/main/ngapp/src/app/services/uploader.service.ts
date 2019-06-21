import { Injectable } from '@angular/core';
import {Administrator, Uploader} from '../types/users';
import {HttpClient} from '@angular/common/http';
import {AbstractUserService} from './abstract-user.service';

@Injectable({
  providedIn: 'root'
})
export class UploaderService extends AbstractUserService<Uploader> {

  constructor(httpClient: HttpClient) {
    super(httpClient);

    this.baseUrl = 'uploaders';
  }

}
