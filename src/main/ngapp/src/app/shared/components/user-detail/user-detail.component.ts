import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Administrator, Consumer, Uploader} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {isNotBlank} from '../../utils/utils';
import {Location} from '@angular/common';
import {AuthService} from '../../../services/auth.service';
import {NotificationService} from '../../../services/notification.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit, OnDestroy {

  @Input() loading = false;
  @Input() signupMode = false;
  @Input() type: 'Administrator' | 'Uploader' | 'Consumer' = 'Administrator';
  @Input() user: Administrator | Uploader | Consumer = null;
  @Output() save: EventEmitter<Administrator | Uploader | Consumer> = new EventEmitter();

  form: FormGroup = null;
  private notification: Subscription;


  get isConsumer(): boolean {
    return this.type === 'Consumer';
  }

  constructor(private notificationService: NotificationService, private actr: ActivatedRoute, private location: Location, private authService: AuthService) {
  }

  ngOnInit() {
    console.log('user --', this.user);
    this.createForm(this.user);

    this.notification = this.notificationService.operation.subscribe(status => {
      if (status.status === 'STOP') {
        this.loading = false;
      }
    });
  }

  createForm(detail: Administrator | Uploader | Consumer) {
    if (this.type === 'Consumer') {
      this.form = new FormGroup({
        id: new FormControl(detail.id),
        displayName: new FormControl(detail.displayName, [Validators.required, Validators.maxLength(64)]),
        codiceFiscale: new FormControl((detail as Consumer).codiceFiscale, [Validators.required, Validators.minLength(16), Validators.maxLength(16)]),
        email: new FormControl(detail.email, [Validators.required, Validators.email, Validators.maxLength(64)]),
        password: new FormControl(detail.password, [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
        username: new FormControl(detail.username, [Validators.required, Validators.email, Validators.maxLength(64)])
      });
    } else {
      this.form = new FormGroup({
        id: new FormControl(detail.id),
        displayName: new FormControl(detail.displayName, [Validators.required, Validators.maxLength(64)]),
        email: new FormControl(detail.email, [Validators.required, Validators.email, Validators.maxLength(64)]),
        password: new FormControl(detail.password, [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
        username: new FormControl(detail.username, [Validators.required, Validators.email, Validators.maxLength(64)])
      });
    }


    if (isNotBlank(detail.id)) {
      // username viene messo in readonly this.form.get('username').
      this.form.get('password').disable();
    }

    console.log('1. this.form.value', this.form.value);
  }

  get isJustCreated(): boolean {
    return !this.alreadyExists;
  }

  get alreadyExists(): boolean {
    return isNotBlank(this.user.id);
  }

  goBack(): void {
    console.log('go back');
    this.location.back();
  }

  onSave() {
    if (this.form.invalid === true) {
      console.log('INVALIDO!!', this.form);
      return;
    }

    // oramai è avviato
    this.loading = true;
    console.log('VALIDO!!', this.form);
    console.log('this.form.value = ', this.form.value);

    // this.save.emit(this.form.value);
    this.save.emit(this.form.value as Administrator);

  }

  ngOnDestroy(): void {
    this.notification.unsubscribe();
  }
}
