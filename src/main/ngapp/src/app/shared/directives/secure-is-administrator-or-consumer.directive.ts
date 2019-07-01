import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {isBlank} from '../utils/utils';
import {AuthService} from '../../services/auth.service';

@Directive({
  selector: '[appSecureIsAdministratorOrConsumer]'
})
export class SecureIsAdministratorOrConsumerDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecureIsAdministratorOrConsumer = 'true';

  private hasView = false;

  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef, public renderer: Renderer2, private authService: AuthService) {
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
    if (isBlank(this.appSecureIsAdministratorOrConsumer)) {
      this.appSecureIsAdministratorOrConsumer = 'true';
    }

    const tagValue = this.appSecureIsAdministratorOrConsumer === 'true';
    const role = this.authService.hasRoleAdministrator() || this.authService.hasRoleConsumer();

    if (role === tagValue && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (role !== tagValue && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }

  }

}
