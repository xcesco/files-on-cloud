import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {isBlank} from '../utils/utils';
import {AuthService} from '../../services/auth.service';

@Directive({
  selector: '[appSecurityIsAdministrator]'
})
export class SecurityIsAdministratorDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecurityIsAdministrator = 'true';

  private hasView = false;

  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef, public renderer: Renderer2, private authService: AuthService) {
  }

  ngOnInit() {
    this.checkPermission();

    // this.profileSubscriber = this.authService.roleSubject.subscribe(result => {
    //   this.checkPermission();
    // });
  }

  public ngOnDestroy() {
    this.profileSubscriber.unsubscribe();
  }

  private checkPermission(): void {
    if (isBlank(this.appSecurityIsAdministrator)) {
      this.appSecurityIsAdministrator = 'true';
    }

    const tagValue = this.appSecurityIsAdministrator === 'true' ? true : false;
    // const role = this.authService.isProduttore();

    // if (role === tagValue && !this.hasView) {
    //   this.viewContainer.createEmbeddedView(this.templateRef);
    //   this.hasView = true;
    // } else if (role !== tagValue && this.hasView) {
    //   this.viewContainer.clear();
    //   this.hasView = false;
    // }

  }

}
