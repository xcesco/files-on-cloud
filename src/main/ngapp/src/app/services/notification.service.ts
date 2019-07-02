import { Injectable } from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  operation: Subject<{status: 'START' | 'STOP'}> = new Subject<{status: 'START' | 'STOP'}>();

  constructor() { }

  notifyOperation(status: {status: 'START' | 'STOP'}) {
    this.operation.next(status);
  }
}
