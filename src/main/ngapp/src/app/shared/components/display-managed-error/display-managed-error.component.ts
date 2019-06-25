import {Component, Host, Input, OnInit, SkipSelf} from '@angular/core';
import {FormGroupDirective} from '@angular/forms';

@Component({
  selector: 'app-display-managed-error',
  templateUrl: './display-managed-error.component.html',
  styles: []
})
export class DisplayManagedErrorComponent implements OnInit {

  @Input() controlName: string;
  // @Input() errorKey: string;

  constructor(
    @Host() @SkipSelf() private form: FormGroupDirective
  ) {
  }

  ngOnInit() {
  }

  get isInvalid() {
    const control = this.form.form.get(this.controlName);
    return control.invalid && control.dirty;
  }

}
