import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {isBlank} from '../utils/utils';
import {AuthService} from '../../services/auth.service';

@Directive({
  selector: '[appSecureIsAdministrator]'
})
export class SecureIsAdministratorDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecureIsAdministrator = 'true';

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
    if (isBlank(this.appSecureIsAdministrator)) {
      this.appSecureIsAdministrator = 'true';
    }

    const tagValue = this.appSecureIsAdministrator === 'true';
    const role = this.authService.hasRoleAdministrator();

    if (role === tagValue && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (role !== tagValue && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }

  }

}
