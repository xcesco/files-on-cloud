import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Administrator, Consumer, Uploader} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {isNotBlank} from '../../utils/utils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  @Input() type: 'Administrator' | 'Uploader' | 'Consumer' = 'Administrator';
  @Input() user: Administrator | Uploader | Consumer = null;
  @Output() save: EventEmitter<Administrator | Uploader | Consumer> = new EventEmitter();

  form: FormGroup = null;

  get isConsumer(): boolean {
    return this.type === 'Consumer';
  }

  constructor(private actr: ActivatedRoute, private location: Location) {
  }

  ngOnInit() {
    console.log('user --', this.user);
    this.createForm(this.user);
  }

  createForm(detail: Administrator | Uploader | Consumer) {

    if (this.type === 'Consumer') {
      this.form = new FormGroup({
        id: new FormControl(detail.id),
        displayName: new FormControl(detail.displayName, [Validators.required]),
        codiceFiscale: new FormControl((detail as Consumer).codiceFiscale, [Validators.required]),
        email: new FormControl(detail.email, [Validators.required, Validators.email]),
        password: new FormControl(detail.password, [Validators.required]),
        username: new FormControl(detail.username, [Validators.required, Validators.email])
      });
    } else {
      this.form = new FormGroup({
        id: new FormControl(detail.id),
        displayName: new FormControl(detail.displayName, [Validators.required]),
        email: new FormControl(detail.email, [Validators.required, Validators.email]),
        password: new FormControl(detail.password, [Validators.required]),
        username: new FormControl(detail.username, [Validators.required, Validators.email])
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

    console.log('VALIDO!!', this.form);
    console.log('this.form.value = ', this.form.value);
    // this.save.emit(this.form.value);
    this.save.emit(this.form.value as Administrator);

  }
}
