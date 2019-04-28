import {Component, OnInit} from '@angular/core';
import {FirebaseuiAngularLibraryService, FirebaseUISignInFailure, FirebaseUISignInSuccessWithAuthResult} from 'firebaseui-angular';
import {AngularFireAuth} from '@angular/fire/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  title = 'ngapp';

  constructor(private firebaseuiAngularLibraryService: FirebaseuiAngularLibraryService,
              private afAuth: AngularFireAuth
              ) {
    firebaseuiAngularLibraryService.firebaseUiInstance.disableAutoSignIn();
  }

  successCallback(signInSuccessData: FirebaseUISignInSuccessWithAuthResult) {
    console.log('login!!');
    console.log(signInSuccessData.authResult);
    // signInSuccessData.authResult.user.

  }

  logout() {
    this.afAuth.auth.signOut().then(() => {
      console.log('esco');
    });
  }

  errorCallback(errorData: FirebaseUISignInFailure) {
    console.log('errore!!');
  }

  ngOnInit() {
  }

}
