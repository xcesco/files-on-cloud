import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs';
import {JwtUser} from '../../types/auth.type';

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

  constructor(private authService: AuthService) {
    this.logged = false;
    this.subscriber = authService.userLoggedSubject.subscribe((user: JwtUser) => {
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

}
