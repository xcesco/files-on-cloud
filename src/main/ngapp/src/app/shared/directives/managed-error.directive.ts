import {Directive, Host, HostBinding, Self, SkipSelf} from '@angular/core';
import {FormGroupDirective, NgControl} from '@angular/forms';

@Directive({
  selector: '[appManagedError]'
})
export class ManagedErrorDirective {

  constructor(
    @Host() @SkipSelf() private form: FormGroupDirective,
    @Self() private control: NgControl
  ) {
  }

  @HostBinding('class.is-invalid')
  get isInvalid(): boolean {
    return this.control.invalid && this.control.dirty;
  }

}
