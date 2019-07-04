import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs';
import {JwtUser} from '../../types/auth.type';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  navbarOpen = false;
  private subscriber: Subscription;
  user: JwtUser;
  logged: boolean;

  constructor(private authService: AuthService, private router: Router) {
    this.logged = false;
    this.user = authService.user;
    this.logged = this.user !== null;
    this.subscriber = authService.userLoggedSubject.subscribe((user: JwtUser) => {
      console.log('sssssssaaa', user);
      if (user === null) {
        this.user = null;
      } else {
        this.user = user;
      }
      this.logged = user !== null;
    });

  }

  ngOnInit() {
  }

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  ngOnDestroy(): void {
    this.subscriber.unsubscribe();
  }

  gotoDetail() {
    if (this.authService.hasRoleAdministrator()) {
      this.router.navigate(['administrators', this.authService.user.id]);
    } else if (this.authService.hasRoleUploader()) {
      this.router.navigate(['uploaders', this.authService.user.id]);
    } else if (this.authService.hasRoleConsumer()) {
      this.router.navigate(['consumers', this.authService.user.id]);
    }
  }
}
