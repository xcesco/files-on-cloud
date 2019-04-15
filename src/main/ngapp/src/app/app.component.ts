import {Component} from '@angular/core';
import {FirebaseuiAngularLibraryService, FirebaseUISignInFailure, FirebaseUISignInSuccessWithAuthResult} from 'firebaseui-angular';
import {AngularFireAuth} from '@angular/fire/auth';

// https://www.npmjs.com/package/firebaseui-angular
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ngapp';


}
