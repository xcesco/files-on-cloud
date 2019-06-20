import {Component} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import {AuthService} from './security/auth.service';
import {ActorsService} from './services/actors.service';

// https://www.npmjs.com/package/firebaseui-angular
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ngapp';


  constructor(
    public authService: AuthService,
    private angularFireAuth: AngularFireAuth,
    private actorsService: ActorsService) {
    this.angularFireAuth.authState.subscribe(this.firebaseAuthChangeListener);
  }

  listAdmins(): void {
    this.actorsService.findAllAdmins().subscribe(result => {
      console.log('done');
    });
  }

  private firebaseAuthChangeListener(response) {
    // if needed, do a redirect in here
    if (response) {
      console.log('Logged in :)');
    } else {
      console.log('Logged out :(');
    }
  }

}
