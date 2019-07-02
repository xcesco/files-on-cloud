# Files on cloud
![logo](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/logo_256.png)

_di Francesco Benincasa_

## Introduzione
Nel realizzare il progetto _Files-On-Cloud_, si è cercato di essere il più aderente possibile alle specifiche di progetto. Si sono apportati i seguenti aggiunte/cambiamenti rispetto alle specifiche iniziali:
Tutti gli utenti sono caratterizzati da uno username in formato email. Questo cambio di requisito si e’ reso necessario al fine di utilizzare Firebase come sistema di autenticazione.
Per gli utenti Consumer esiste un campo Codice Fiscale. In realtà, pur avendo un vincolo di unicità per tutti gli user di tipo consumer, questo campo deve essere visto come un semplice codice univoco. Non vengono eseguiti controlli di validità di tale campo come codice fiscale.
Al fine semplificare la dimostrazione del funzionamento dell’applicazione, al momento della creazione dei vari tipi di utenti, alcuni campi sono prevalorizzati. Ovviamente l’utente è libero di cambiarli a proprio piacimento prima di salvare. Ad esempio, ogni utente in fase di creazione ha come password: password e come indirizzo di posta elettronica uxcesco@gmail.com (per rendere la verifica del funzionamento del sistema più semplice).
Sono stati inseriti dei limiti in termini di dimensioni dei file da caricare di 1MB.
Il report per gli amministratori di default prende come data iniziale quella del primo giorno del mese precedente e come data finale la data odierna.

L’applicazione e’ visionabile all’indirizzo:

[https://programmazione-web-238419.appspot.com/](https://programmazione-web-238419.appspot.com/)

I sorgenti sono disponibili su github:

[https://github.com/xcesco/files-on-cloud](https://github.com/xcesco/files-on-cloud)

Di seguito è riportato uno screenshot dell’applicativo

![logo](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/screenshot.png)



## Piattaforma tecnologica utilizzata
La soluzione software realizzata per essere eseguita sulla piattaforma cloud di Google. In particolare si e’ utilizzato:

 - Google App Engine: per ospitare l’applicazione web.
 - Google SQL: per ospitare l’istanza di database MYSQL utilizzata da Files-on-cloud.
 - Firebase Authenticator: per la gestione degli utenti e relativi login.
 - Google storage per memorizzare i file da condividere.

Per l’invio di email ci si è basati sul sistema di mail offerto dalla piattaforma (che in locale non funziona).



## Tecnologie utilizzate
Si sono utilizzate diverse tecnologie per realizzare il progetto. Di seguito sono riportate quelle principali suddivise per “area di competenza”:

### Linguaggi utilizzati
- Java per il backend
- Typescript per il frontend
- CSS
- HTML

### Gestione del progetto e dei sorgenti
- Maven
- Git

### Backend
- Spring Boot
- Spring
- Spring security
- Spring web
- JPA/Hibernate
- FasterXML/Jackson: libreria per la conversione oggetti Java in JSON
- Swagger: per la documentazione ed il testing dei servizi web realizzati con l’applicazione.
- Firebase Admin SDK

### Frontend
- Angular 8
- Bootstrap
- NG-Bootstrap
- Font-awesome
- Firebase Client
- Node.JS (npm)

### Tools ed ambienti di sviluppo utilizzati
- Eclipse (per il backend e la gestione del progetto in maven)
- Plugin eclipse per Integrazione con Google Cloud SDK
- Webstorm (per la gestione del progetto Angular)
- DataGrip
- Google Cloud SDK
- Maven
- Atom

Gli strumenti di sviluppo sono stati scelti in quanto già utilizzati in altri progetti. Le scelte tecnologie sono state guidate dal fatto che:
Sono tra quelle più usate attualmente nel mercato
In buona parte già note



## Configurazione cloud
Per la realizzazione del progetto è stato creato un apposito account gmail con il quale si sono creati due progetti, uno su [https://console.cloud.google.com](https://console.cloud.google.com) ed uno su [https://console.firebase.google.com/](https://console.firebase.google.com/) .



## Note sulla configurazione dell’ambiente di sviluppo

![struttura del progetto](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/struttura_progetto.png)

Il progetto è suddiviso in due moduli:
- Il modulo _Maven_, che si occupa del progetto Java e della build complessiva del progetto. Al suo interno vengono utilizzate le credenziali di servizio generate mediante la console del progetto su cloud.

[https://cloud.google.com/iam/docs/creating-managing-service-account-keys](https://cloud.google.com/iam/docs/creating-managing-service-account-keys)

![cgloud-console.png](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/cgloud-console.png)

Il file JSON che ospita la service key è reso disponibile all’applicazione web sotto forma di variabile d’ambiente _GOOGLE_APPLICATION_CREDENTIALS_ (contiene il path del file). 

![secret_json.png](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/secret_json.png)

Questo file verrà recupero dall’app in fase di avvio mediante il codice contenuto nella classe _WebStartup_.

![java.png](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/java.png)

Il modulo _Angular_ ospita il codice del front-end realizzato in Angular. Questo modulo richiede la configurazione del progetto su firebase. 

Le istruzioni su come ottenere le credenziali da usare con Firebase sono presenti al seguente url:

[https://firebase.google.com/docs/web/setup](https://firebase.google.com/docs/web/setup)

![firebase-console.png](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/firebase-console.png)

Le credenziali ottenute devono presenti nei file _environment.prod.ts_ ed _environment.ts_.

![environment.png](https://github.com/xcesco/files-on-cloud/blob/master/docs/imgs/environment.png)

## Link utili
Di seguito sono risportati alcuni link utilizzati durante lo sviluppo


### Spring
- [https://medium.com/google-cloud/getting-started-with-google-app-engine-and-spring-boot-in-5-steps-2d0f8165c89](https://medium.com/google-cloud/getting-started-with-google-app-engine-and-spring-boot-in-5-steps-2d0f8165c89)
- https://stackoverflow.com/questions/40610155/how-to-deploy-spring-boot-application-on-google-app-enginegae-using-eclipse-id
- https://stackoverflow.com/questions/31821617/how-to-deploy-a-springboot-rest-application-to-google-app-engine
- https://www.baeldung.com/spring-xml-vs-java-config
- https://github.com/GoogleCloudPlatform/getting-started-java/

### Template
- https://startbootstrap.com/templates/sb-admin/

### Rest upload
- https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

### H2 config per sviluppo local
- https://dzone.com/articles/integrate-h2-database-in-your-spring-boot-applicat

### Eclipse Plugin
- http://download.eclipse.org/egit/github/updates

### Configurazione Cloud SQL
- https://cloud.google.com/appengine/docs/standard/php/cloud-sql/using-cloud-sql-mysql

### BCrypt
- https://docs.spring.io/spring-security/site/docs/5.0.0.BUILD-SNAPSHOT/api/org/springframework/security/crypto/bcrypt/BCrypt.html
- https://www.devglan.com/spring-security/spring-boot-security-password-encoding-bcrypt-encoder


### Spring
- https://www.baeldung.com/spring-boot-security-autoconfiguration

### Spring Rest Validation
- https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/

### Angular & Firebase Authentication
- https://auth0.com/blog/how-to-authenticate-firebase-and-angular-with-auth0-part-1/
- https://itnext.io/part-2-complete-step-by-step-firebase-authentication-in-angular-2-25d284102632
- https://medium.com/@jwngr/demystifying-firebase-auth-tokens-e0c533ed330c

### JPA Repository
- https://thoughts-on-java.org/complete-guide-inheritance-strategies-jpa-hibernate/
- https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito

### Spring Cloud SQL
 - [link 1](https://medium.com/@DazWilkin/google-cloud-sql-6-ways-golang-a4aa497f3c67)
 - [link 2](https://medium.com/@DazWilkin/google-cloud-sql-4-ways-spring-a4f13ad32714)
 - [link 3](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-gcp-starter-sql-mysql)

 - [link 4](https://cloud.google.com/bigtable/docs/samples-overview)
 - [link 5](https://cloud.google.com/bigtable/docs/tutorials)
 - [link 6](https://cloud.google.com/appengine/docs/flexible/java/using-cloud-sql)
 - [link 7](https://github.com/GoogleCloudPlatform/appengine-cloudsql-native-mysql-hibernate-jpa-demo-java/blob/master/pom.xml)
 
 - [installazione proxy](https://cloud.google.com/sql/docs/mysql/quickstart-proxy-test)

### Spring Cloud Storage
 - [java-google-cloud-storage](https://www.baeldung.com/java-google-cloud-storage)
 - [run-spring-petclinic-on-app-engine-cloudsql](https://cloud.google.com/community/tutorials/run-spring-petclinic-on-app-engine-cloudsql)

### Hibernate conversione Set in un field
- https://www.baeldung.com/hibernate-persist-json-object

### Send Mail
 - [link 1](https://cloud.google.com/appengine/docs/standard/java/mail/)
 - [link 2](https://cloud.google.com/appengine/docs/standard/java/mail/sendgrid)
 - https://cloud.google.com/appengine/docs/standard/java/mail/sending-mail-with-mail-api
 - https://github.com/GoogleCloudPlatform/java-docs-samples/blob/2e5996c68440134a79f1511c57529fa5cf987628/appengine-java8/mail/src/main/java/com/example/appengine/mail/MailServlet.java
 
### Collegare source su github
- [Link](https://cloud.google.com/debugger/docs/source-context?hl=en_US&_ga=2.30533503.-1121481467.1557264458)
 

### Codifice fiscale
Elenco dei codici fiscali generati da http://www.codicefiscaleonline.com/
 - Mario Rossi RSSMRA80A01L424F
 - Vittoria Rossi RSSVTR80A01L424L
 
### Html sanitazier
- https://github.com/OWASP/java-html-sanitizer
 
### Autentication
- https://www.techiediaries.com/angular-course-firebase-authentication/
- https://github.com/techiediaries/angular-portfolio
- https://www.baeldung.com/spring-security-method-security


### Angular
- https://codeburst.io/understanding-resolvers-in-angular-736e9db71267

### Toaster dialog
- https://stackblitz.com/edit/angular-confirmation-dialog

### Toaster
- https://www.npmjs.com/package/ngx-toastr

### Login con firebase
- https://www.techiediaries.com/angular-course-firebase-authentication/
- https://hatchful.shopify.com/onboarding/select-logo

### Generazione avatar
- https://getavataaars.com/ 