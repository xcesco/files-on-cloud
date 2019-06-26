import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Administrator} from '../../../types/users';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {isNotBlank} from '../../utils/utils';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  @Input() user: Administrator = null;

  @Output() save: EventEmitter<Administrator> = new EventEmitter();

  form: FormGroup = null;

  constructor(private actr: ActivatedRoute) {
  }

  ngOnInit() {
    console.log('user --', this.user);
    this.createForm(this.user);
  }

  createForm(detail: Administrator) {
    this.form = new FormGroup({
      id: new FormControl(detail.id),
      displayName: new FormControl(detail.displayName, [Validators.required]),
      email: new FormControl(detail.email, [Validators.required, Validators.email]),
      password: new FormControl(detail.password, [Validators.required]),
      username: new FormControl(detail.username, [Validators.required, Validators.email])
    });

    if (isNotBlank(detail.id)) {
      this.form.get('username').disable();
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
