package org.abubusoft.foc.web.controllers;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Application Lifecycle Listener implementation class Startup
 *
 */
@WebListener
public class Startup implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public Startup() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		try {
			//FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

			FirebaseOptions options;

			options = new FirebaseOptions.Builder()
					// prende le credenziali da GOOGLE_APPLICATION_CREDENTIALS
					.setCredentials(GoogleCredentials.getApplicationDefault())
					//.setCredentials(GoogleCredentials.fromStream(serviceAccount))					
					.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
			
			//FirebaseApp.
			
				} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
