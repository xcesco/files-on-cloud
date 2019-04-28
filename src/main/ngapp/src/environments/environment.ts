// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {

  API_URL: '../api/v1/',

  production: false,
  firebase: {
    apiKey: 'AIzaSyDHRei9lTBnZDkK2HA9XDybwO0UKAEsLqY',
    authDomain: 'programmazione-web-238419.firebaseapp.com',
    databaseURL: 'https://programmazione-web-238419.firebaseio.com',
    projectId: 'programmazione-web-238419',
    storageBucket: 'programmazione-web-238419.appspot.com',
    messagingSenderId: '249732682505'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
