package org.abubusoft.foc.web;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.abubusoft.foc.business.facades.Populator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Serve ad inizializzare GCLOUD (le credenziali sono prese direttamente
 * dell'environment)
 */
@WebListener
public class WebStartup implements ServletContextListener {

	@Autowired
	public void setPopulator(Populator populator) {
		this.populator = populator;
	}

	private Populator populator;



	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			FileInputStream serviceAccount =
					new FileInputStream("/Users/xcesco/Dropbox/ProgrammazioneWeb/secured/programmazione-web-238419/serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);
//			FirebaseOptions options;
//
//			options = new FirebaseOptions.Builder()
//					// prende le credenziali da GOOGLE_APPLICATION_CREDENTIALS
//					.setCredentials(GoogleCredentials.getApplicationDefault())
//					// .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//					.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com").build();
//
//			FirebaseApp.initializeApp(options);

			populator.execute();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
