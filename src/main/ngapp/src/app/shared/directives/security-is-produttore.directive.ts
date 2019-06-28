import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthService} from '../services/auth.service';
import {isBlank} from '../utils/custom-validators';

@Directive({
  selector: '[appSecurityIsProduttore]'
})
export class SecurityIsProduttoreDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecurityIsProduttore = 'true';

  private hasView = false;

  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef, public renderer: Renderer2, private authService: AuthService) {
  }

  ngOnInit() {
    this.checkPermission();

    this.profileSubscriber = this.authService.roleSubject.subscribe(result => {
      this.checkPermission();
    });
  }

  public ngOnDestroy() {
    this.profileSubscriber.unsubscribe();
  }

  private checkPermission(): void {
    if (isBlank(this.appSecurityIsProduttore)) {
      this.appSecurityIsProduttore = 'true';
    }

    const tagValue = this.appSecurityIsProduttore === 'true' ? true : false;
    const role = this.authService.isProduttore();

    if (role === tagValue && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (role !== tagValue && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }

   /* if (role !== tagValue) {
      this.renderer.addClass(this.el.nativeElement, 'security-hide');
    } else {
      this.renderer.removeClass(this.el.nativeElement, 'security-hide');
    }*/
  }

}
