package org.abubusoft.foc.web;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Serve ad inizializzare GCLOUD (le credenziali sono prese direttamente
 * dell'environment)
 */
@WebListener
public class WebStartup implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		try {

			FirebaseOptions options;

			options = new FirebaseOptions.Builder()
					// prende le credenziali da GOOGLE_APPLICATION_CREDENTIALS
					.setCredentials(GoogleCredentials.getApplicationDefault())
					// .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com").build();

			FirebaseApp.initializeApp(options);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
