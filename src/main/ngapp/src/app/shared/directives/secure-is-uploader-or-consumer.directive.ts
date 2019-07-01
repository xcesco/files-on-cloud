import {Directive, Input, OnDestroy, OnInit, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';
import {Subscription} from 'rxjs';
import {isBlank} from '../utils/utils';
import {AuthService} from '../../services/auth.service';

@Directive({
  selector: '[appSecureIsUploaderOrConsumer]'
})
export class SecureIsUploaderOrConsumerDirective implements OnInit, OnDestroy {

  profileSubscriber: Subscription;

  @Input() appSecureIsUploaderOrConsumer = 'true';

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
    if (isBlank(this.appSecureIsUploaderOrConsumer)) {
      this.appSecureIsUploaderOrConsumer = 'true';
    }

    const tagValue = this.appSecureIsUploaderOrConsumer === 'true';
    const role = this.authService.hasRoleConsumer() || this.authService.hasRoleUploader();

    if (role === tagValue && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (role !== tagValue && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }

  }

}
