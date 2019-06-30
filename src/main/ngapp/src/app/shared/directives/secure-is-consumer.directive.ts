import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthService} from '../../services/auth.service';
import {isBlank} from '../utils/utils';

@Directive({
  selector: '[appSecureIsConsumer]'
})
export class SecureIsConsumerDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecureIsConsumer = 'true';

  private hasView = false;

  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef, private authService: AuthService) {
  }

  ngOnInit() {
    this.checkPermission();

    this.profileSubscriber = this.authService.userLoggedSubject.subscribe(result => {
      this.checkPermission();
    });
  }

  public ngOnDestroy() {
    this.profileSubscriber.unsubscribe();
  }

  private checkPermission(): void {
    if (isBlank(this.appSecureIsConsumer)) {
      this.appSecureIsConsumer = 'true';
    }

    const tagValue = this.appSecureIsConsumer === 'true';
    const role = this.authService.hasRoleConsumer();

    if (role === tagValue && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (role !== tagValue && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }

  }
}
